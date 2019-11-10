package team14.arms.ui.views.login;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.templatemodel.TemplateModel;

import team14.arms.ui.utils.Constants;

@Tag("login-view")
@HtmlImport("src/views/login/login-view.html")
@Route(value = "login")
@PageTitle("ARMS : " + Constants.TITLE_LOGIN)
@Viewport(Constants.VIEWPORT)
public class LoginView extends PolymerTemplate<LoginView.Model> implements PageConfigurator, AfterNavigationObserver {

    public LoginView() {}

    @Override
    public void configurePage(InitialPageSettings settings) {
        // Force login page to use Shady DOM to avoid problems with browsers and
        // password managers not supporting shadow DOM
        settings.addInlineWithContents(InitialPageSettings.Position.PREPEND,
            "if (window.customElements) {" +
                "window.customElements.forcePolyfill=true;" +
                "window.ShadyDOM={force:true}" +
            "};", InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        boolean error = event.getLocation().getQueryParameters().getParameters().containsKey("error");
        getModel().setError(error);
    }

    public interface Model extends TemplateModel {
        void setError(boolean error);
    }

}
