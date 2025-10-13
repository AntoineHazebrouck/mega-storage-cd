package antoine.mega_storage_cd.views.home.components;

import antoine.mega_storage_cd.entitites.Cd;
import antoine.mega_storage_cd.services.mixins.CdsRepositoryMixin;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import java.util.Objects;
import java.util.Optional;

public class CdBinder extends Binder<Cd> implements CdsRepositoryMixin {

    public CdBinder(IntegerField position, TextField artist, TextField album) {
        super(Cd.class);
        this.forField(position)
            .asRequired(
                IntegerRangeValidator.of(
                    "Position must be between 1 and 300",
                    1,
                    300
                )
            )
            .asRequired((value, context) ->
                findCdById(Optional.ofNullable(value).orElse(-1)).isPresent()
                    ? ValidationResult.error(
                        "Position" + value + " is already filled"
                    )
                    : ValidationResult.ok()
            )
            .bind(Cd::getPosition, Cd::setPosition);
        this.forField(artist)
            .asRequired("Artist is empty")
            .bind(Cd::getArtist, Cd::setArtist);
        this.forField(album)
            .asRequired("Album is empty")
            .bind(Cd::getAlbum, Cd::setAlbum);
    }

    public CdBinder withAlreaderFilledValidator(Optional<Cd> currentCd) {
        this.withValidator((newCd, context) -> {
                if (
                    Objects.equals(
                        newCd.getPosition(),
                        currentCd.orElseThrow().getPosition()
                    )
                ) {
                    return ValidationResult.ok();
                } else {
                    return findCdById(newCd.getPosition()).isPresent()
                        ? ValidationResult.error(
                            "Position" + newCd + " is already filled"
                        )
                        : ValidationResult.ok();
                }
            });
        return this;
    }

    public void handleInvalidForm() {
        this.validate();
        Notification.show(
            "The form is not filled properly",
            4000,
            Position.TOP_CENTER,
            true
        );
    }
}
