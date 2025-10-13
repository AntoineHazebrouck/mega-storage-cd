package antoine.mega_storage_cd.views.home.components;

import antoine.mega_storage_cd.entitites.Cd;
import antoine.mega_storage_cd.services.mixins.CdsRepositoryMixin;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import lombok.val;

public class CdsGrid extends Composite<Grid<Cd>> implements CdsRepositoryMixin {

    Grid<Cd> grid = new Grid<>(Cd.class, false);

    @Override
    protected Grid<Cd> initContent() {
        val position = grid
            .addColumn(Cd::getPosition)
            .setSortable(true)
            .setHeader("Position");
        grid.addColumn(Cd::getArtist).setSortable(true).setHeader("Artist");
        grid.addColumn(Cd::getAlbum).setSortable(true).setHeader("Album");

        grid.addComponentColumn(cd ->
            new Button("Delete", event -> {
                deleteCdById(cd.getPosition());
                refreshItems();
            })
        );

        grid.sort(GridSortOrder.asc(position).build());

        refreshItems();
        return grid;
    }

    public void refreshItems() {
        grid.setItems(findAllCds());
    }
}
