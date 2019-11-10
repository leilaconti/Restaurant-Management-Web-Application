package team14.arms.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.PWA;

import team14.arms.backend.security.SecurityUtils;
import team14.arms.ui.exceptions.AccessDeniedException;
import team14.arms.ui.views.admin.users.UsersView;
import team14.arms.ui.views.HasConfirmation;
import team14.arms.ui.views.login.LoginView;

import static team14.arms.ui.utils.Constants.*;

@Viewport(VIEWPORT)
@PWA(name = "A Restaurant Management System", shortName = "ARMS", startPath = "login", backgroundColor = "#227AEF", themeColor = "#227AEF")
public class MainView extends AbstractAppRouterLayout implements BeforeEnterObserver {

    private final ConfirmDialog confirmDialog;

    public MainView() {
        this.confirmDialog = new ConfirmDialog();
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmButtonTheme("raised tertiary error");
        confirmDialog.setCancelButtonTheme("raised tertiary");
        getElement().appendChild(confirmDialog.getElement());
    }

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {

        appLayout.setBranding(new Span("A Restaurant Management System"));

        if (SecurityUtils.isUserLoggedIn()) {

            if (SecurityUtils.isAccessGranted(UsersView.class)) {
                setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.USER.create(), TITLE_USERS, PAGE_USERS));
            }

            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.SIGN_OUT.create(), TITLE_LOGOUT, e -> UI.getCurrent().getPage().executeJavaScript("location.assign('logout')")));

        }

        getElement().addEventListener("search-focus", e -> {
            appLayout.getElement().getClassList().add("hide-navbar");
        });

        getElement().addEventListener("search-blur", e -> {
            appLayout.getElement().getClassList().remove("hide-navbar");
        });
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
        if (!accessGranted) {
            if (SecurityUtils.isUserLoggedIn()) {
                event.rerouteToError(AccessDeniedException.class);
            } else {
                event.rerouteTo(LoginView.class);
            }
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        super.showRouterLayoutContent(content);
        this.confirmDialog.setOpened(false);
        if (content instanceof HasConfirmation) {
            ((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
        }
    }

}
