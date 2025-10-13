package antoine.mega_storage_cd.views.home.components;

import antoine.mega_storage_cd.entitites.Cd;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CdForm extends Composite<VerticalLayout> {

    private final Consumer<Cd> callback;

    IntegerField position = new IntegerField("Position");
    TextField artist = new TextField("Artist");
    TextField album = new TextField("Album");

    @Override
    protected VerticalLayout initContent() {
        CdBinder binder = new CdBinder(position, artist, album);

        var row = new FormLayout();
        var addButton = new Button("Add", event -> {
            if (!binder.isValid()) {
                binder.handleInvalidForm();
            } else {
                var newCd = new Cd();
                newCd.setPosition(position.getValue());
                newCd.setArtist(artist.getValue());
                newCd.setAlbum(album.getValue());

                callback.accept(newCd);

                binder.refreshFields();
                position.clear();
                artist.clear();
                album.clear();
            }
        });

        row.add(position, artist, album);
        row.setAutoResponsive(true);
        row.setAutoRows(true);
        return new VerticalLayout(row, addButton);
    }
}
