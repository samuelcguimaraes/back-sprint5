package br.com.rchlo.store.dto;

import br.com.rchlo.store.domain.Color;

public interface ProductByColorDto {

    Color getColor();

    default String getColorDescription() {
        return getColor().getDescription();
    }

    long getAmount();

}
