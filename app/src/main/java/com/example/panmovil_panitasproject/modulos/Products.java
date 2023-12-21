package com.example.panmovil_panitasproject.modulos;

import java.io.Serializable;

public class Products implements Serializable {

    String proId, proNombre, proDescripcion, tproId, proPrecioUnitario, urlImagen;

    public Products(String proId, String proNombre, String proDescripcion, String tproId, String proPrecioUnitario, String urlImagen) {
        this.proId = proId;
        this.proNombre = proNombre;
        this.proDescripcion = proDescripcion;
        this.tproId = tproId;
        this.proPrecioUnitario = proPrecioUnitario;
        this.urlImagen = urlImagen;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public String getTproId() {
        return tproId;
    }

    public void setTproId(String tproId) {
        this.tproId = tproId;
    }

    public String getProPrecioUnitario() {
        return proPrecioUnitario;
    }

    public void setProPrecioUnitario(String proPrecioUnitario) {this.proPrecioUnitario = proPrecioUnitario;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
