package antoine.mega_storage_cd.views.home.components;

import antoine.mega_storage_cd.entitites.Cd;
import antoine.mega_storage_cd.services.mixins.CdsRepositoryMixin;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.textfield.TextField;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.val;

public class CdsGrid extends Composite<Grid<Cd>> implements CdsRepositoryMixin {

    Grid<Cd> grid = new Grid<>(Cd.class, false);
    GridListDataView<Cd> dataView = refreshItems();

    @Override
    protected Grid<Cd> initContent() {
        val position = grid
            .addColumn(Cd::getPosition)
            .setSortable(true)
            .setHeader("Position");
        grid.addColumn(Cd::getArtist).setSortable(true).setHeader("Artist");
        grid.addColumn(Cd::getAlbum).setSortable(true).setHeader("Album");
        grid
            .addColumn(cd ->
                cd.getGenres().stream().collect(Collectors.joining(","))
            )
            .setHeader("Genres");

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

    public GridListDataView<Cd> refreshItems() {
        return grid.setItems(findAllCds());
    }

    public void addSearchFilter(TextField filter) {
        filter.addValueChangeListener(event -> {
            if (event.getValue().isBlank()) dataView.removeFilters();
            else {
                dataView.removeFilters();
                dataView.addFilter(cd ->
                    Stream.concat(
                        Stream.of(
                            cd.getPosition(),
                            cd.getAlbum(),
                            cd.getArtist()
                        ),
                        cd.getGenres().stream()
                    ).anyMatch(field -> {
                        val ok = field.toString().contains(event.getValue());
                        return ok;
                    })
                );
            }
        });
    }
}
