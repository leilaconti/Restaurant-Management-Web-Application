#!/usr/bin/env python3

import json
import os
import shutil
import subprocess
import sys
from argparse import ArgumentParser

from urllib3.util import parse_url

# heroku db url environment variable
HEROKU_DB_VAR = 'DATABASE_URL'
# heroku app name
HEROKU_APP_NAME = 't14-arms'

# path to this script
script_path = os.path.realpath(__file__)
# directory containing script
script_dir = os.path.dirname(script_path)

# path to dbinfo file
creds_file = os.path.join(script_dir, 'dbinfo.json')


class DataSourceInfo:
    """
    A model of the DataSource info to be passed to Spring.
    """

    def __init__(self, url, username, password):
        self.url = url
        self.username = username
        self.password = password

    @staticmethod
    def from_heroku_url(url):
        """
        Constructs a DataSourceInfo from a heroku database url.
        """
        url = parse_url(url)

        # the layout of the heroku url is known, so this should be safe
        ds_url = 'jdbc:postgresql://{host}:{port}{path}'.format(
            host=url.host,
            port=url.port,
            path=url.path
        )
        ds_username = url.auth.split(':')[0]
        ds_password = url.auth.split(':')[1]

        return DataSourceInfo(ds_url, ds_username, ds_password)

    @staticmethod
    def from_dbinfo(file_path, test=False):
        """
        Constructs a DataSourceInfo from a 'dbinfo.json' file.
        """
        # check file exists at path
        if not os.path.isfile(file_path):
            raise RuntimeError('file does not exist: {}'.format(file_path))

        # open file and read json
        with open(file_path, 'r') as f:
            data = json.load(f)

        if test:
            section = 'test'
        else:
            section = 'live'

        data = data[section]

        ds_url = data['url']
        ds_username = data['username']
        ds_password = data['password']

        if ds_url is None:
            raise RuntimeError('{}: url is missing'.format(section))
        if ds_username is None:
            raise RuntimeError('{}: username is missing'.format(section))
        if ds_password is None:
            raise RuntimeError('{}: password is missing'.format(section))

        return DataSourceInfo(ds_url, ds_username, ds_password)


def has_heroku():
    """
    Checks if heroku is installed.
    """
    return shutil.which('heroku') is not None


def get_heroku_url():
    # get database url string from heroku environment variables
    url_bytes = subprocess.check_output(
        ['heroku', 'config:get', HEROKU_DB_VAR,
         '--app={}'.format(HEROKU_APP_NAME)])
    # decode bytes to str
    url = url_bytes.decode(sys.stdout.encoding).strip()

    return url


def get_settings():
    parser = ArgumentParser()
    parser.add_argument('--test', action='store_true')

    return parser.parse_args()


def main():
    # get settings from command line args
    settings = get_settings()

    is_test = settings.test

    if is_test:
        # pull info from 'dbinfo.json' file
        if is_test:
            print('reading test datasource info')
        else:
            print('heroku binary not found in path')
            print('reading datasource info from \'dbinfo.json\' file')

        try:
            ds = DataSourceInfo.from_dbinfo(creds_file, test=is_test)
        except RuntimeError as err:
            print(
                'error: could not read \'dbinfo.json\' file {file}\n'
                'cause: {cause}'.format(
                    file=creds_file,
                    cause=err,
                )
            )
            sys.exit(1)
    else:
        # get database url from heroku
        url = get_heroku_url()

        # parse datasource info from heroku url
        ds = DataSourceInfo.from_heroku_url(url)

    # copy environment variables
    env = os.environ.copy()

    # write spring datasource environment variables
    env['SPRING_DATASOURCE_URL'] = ds.url
    env['SPRING_DATASOURCE_USERNAME'] = ds.username
    env['SPRING_DATASOURCE_PASSWORD'] = ds.password

    subprocess.run(['mvn', 'spring-boot:run'], env=env)


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
