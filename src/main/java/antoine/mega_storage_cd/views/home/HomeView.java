package antoine.mega_storage_cd.views.home;

import antoine.mega_storage_cd.services.mixins.CdsRepositoryMixin;
import antoine.mega_storage_cd.views.home.components.CdForm;
import antoine.mega_storage_cd.views.home.components.CdsGrid;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import lombok.val;

@PageTitle("Mega Storage CD - Home")
@RequiredArgsConstructor
@Route("")
public class HomeView
    extends Composite<VerticalLayout>
    implements CdsRepositoryMixin {

    @Override
    protected VerticalLayout initContent() {
        CdsGrid grid = new CdsGrid();

        val search = new TextField("Search for something");
        grid.addSearchFilter(search);

        return new VerticalLayout(
            new H1("Mega Storage CD"),
            new CdForm(cd -> {
                saveCd(cd);
                grid.refreshItems();
            }),
            search,
            grid
        );
    }
}
