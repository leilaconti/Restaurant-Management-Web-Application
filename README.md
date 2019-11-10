<div align="center">
  <h3>CS2810: Team Project (Team 14)</h3>
  <p>A Restaurant Management System (ARMS)</p>
</div>

[![Build Status](https://travis-ci.com/RHUL-CS-Projects/TeamProject2019_14.svg?token=SRCEWBZueHRVzGg2uwk3&branch=master)](https://travis-ci.com/RHUL-CS-Projects/TeamProject2019_14)

### Build & Run

```
$ python3 launch.py [--test]
```

If the `--test` flag is used, or heroku-cli is not installed (or cannot be found
on the `PATH`), then the script will attempt to pull environment variables for
the database connection info from the `dbinfo.json` file.

Here is an example of what the `dbinfo.json` file should look like:
```json
{
    "test": {
        "url": "jdbc:postgresql://localhost:5432/test",
        "username": "test",
        "password": ""
    },
    "live": {
        "url": "jdbc:postgresql://db.example.com/live",
        "username": "user",
        "password": "password123"
    }
}
```

The `dbinfo.json` should not be committed to the repository and is included in
the ignore patterns of the `.gitignore` file.


### Initialise Database

When initialising a database in order to create the tables used by the
application, the `spring.jpa.hibernate.ddl-auto` setting in `application.yaml`
should be set to `create`.

Outside of creation of the database tables this
setting should be set to `none`. If the setting is set to `create` and tables
already exist, they will be dropped and recreated automatically.
