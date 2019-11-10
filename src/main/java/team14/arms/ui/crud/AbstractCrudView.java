package team14.arms.ui.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import elemental.json.Json;
import java.util.function.Consumer;

import team14.arms.backend.data.entity.AbstractEntity;
import team14.arms.backend.data.entity.util.EntityUtil;
import team14.arms.backend.security.CurrentUser;
import team14.arms.backend.service.FilterableCrudService;
import team14.arms.ui.components.SearchBar;
import team14.arms.ui.views.HasNotifications;

public abstract class AbstractCrudView<E extends AbstractEntity> extends Crud<E> implements HasUrlParameter<Long>, HasNotifications {

    private static final String DISCARD_MESSAGE = "There are unsaved changes to the %s. Discard changes?";
    private static final String DELETE_MESSAGE = "Are you sure you want to delete the selected %s? This action can't be undone.";

    private final Grid<E> grid;

    private final CrudEntityHandler<E> entityHandler;

    protected abstract String getBasePage();

    protected abstract void setupGrid(Grid<E> grid);

    public AbstractCrudView(Class<E> beanType, FilterableCrudService<E> service, Grid<E> grid, CrudEditor<E> editor, CurrentUser currentUser) {
        super(beanType, grid, editor);
        this.grid = grid;
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        CrudI18n crudI18n = CrudI18n.createDefault();
        String entityName = EntityUtil.getName(beanType);
        crudI18n.setNewItem("New " + entityName);
        crudI18n.setEditItem("Edit " + entityName);
        crudI18n.setEditLabel("Edit " + entityName);
        crudI18n.getConfirm().getCancel().setContent(String.format(DISCARD_MESSAGE, entityName));
        crudI18n.getConfirm().getDelete().setContent(String.format(DELETE_MESSAGE, entityName));
        crudI18n.setDeleteItem("Delete");
        setI18n(crudI18n);

        CrudEntityDataProvider<E> dataProvider = new CrudEntityDataProvider<>(service);
        grid.setDataProvider(dataProvider);
        setupGrid(grid);
        Crud.addEditColumn(grid);

        entityHandler = new CrudEntityHandler<>(service, currentUser, this);

        SearchBar searchBar = new SearchBar();
        searchBar.setActionText("New " + entityName);
        searchBar.setPlaceHolder("Search");
        searchBar.addFilterChangeListener(e -> dataProvider.setFilter(searchBar.getFilter()));
        searchBar.getActionButton().getElement().setAttribute("new-button", true);

        setToolbar(searchBar);

        setupCrudEventListeners(entityHandler);
    }

    private void setupCrudEventListeners(CrudEntityHandler<E> entityHandler) {
        Consumer<E> onSuccess = entity -> navigateToEntity(null);
        Consumer<E> onFail = entity -> {};

        addEditListener(e -> entityHandler.loadEntity(e.getItem().getId(), entity -> navigateToEntity(entity.getId().toString())));
        addCancelListener(e -> navigateToEntity(null));
        addSaveListener(e -> entityHandler.save(e.getItem(), onSuccess, onFail));
        addDeleteListener(e -> entityHandler.delete(e.getItem(), onSuccess, onFail));
    }

    private String generateLocation(String basePage, String entityId) {
        return basePage + (entityId == null || entityId.isEmpty() ? "" : "/" + entityId);
    }

    protected void navigateToEntity(String id) {
        getUI().ifPresent(ui -> ui.navigate(generateLocation(getBasePage(), id)));
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id != null) {
            E item = getEditor().getItem();
            if (item != null && id.equals(item.getId())) return;
            entityHandler.loadEntity(id, this::edit);
        }
    }

    private void edit(E entity) {
        getElement().callFunction("__edit", Json.instance().parse("{\"key\":\"" + grid.getDataCommunicator().getKeyMapper().key(entity) + "\"}"));
    }
}
