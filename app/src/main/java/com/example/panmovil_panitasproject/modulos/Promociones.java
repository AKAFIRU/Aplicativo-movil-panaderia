package com.example.panmovil_panitasproject.modulos;

public class Promociones {

    String promoId, proNombre, tpromoNombre, tpromoDescripcion, promoDescuento, proPrecioUnitario, promoFechaini, promoFechafin, proImagen;

    public Promociones(String promoId, String proNombre, String tpromoNombre, String tpromoDescripcion, String promoDescuento, String proPrecioUnitario, String promoFechaini, String promoFechafin, String proImagen) {
        this.promoId = promoId;
        this.proNombre = proNombre;
        this.tpromoNombre = tpromoNombre;
        this.tpromoDescripcion = tpromoDescripcion;
        this.promoDescuento = promoDescuento;
        this.proPrecioUnitario = proPrecioUnitario;
        this.promoFechaini = promoFechaini;
        this.promoFechafin = promoFechafin;
        this.proImagen = proImagen;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getTpromoNombre() {
        return tpromoNombre;
    }

    public void setTpromoNombre(String tpromoNombre) {
        this.tpromoNombre = tpromoNombre;
    }

    public String getTpromoDescripcion() {
        return tpromoDescripcion;
    }

    public void setTpromoDescripcion(String tpromoDescripcion) {
        this.tpromoDescripcion = tpromoDescripcion;
    }

    public String getPromoDescuento() {
        return promoDescuento;
    }

    public void setPromoDescuento(String promoDescuento) {
        this.promoDescuento = promoDescuento;
    }

    public String getProPrecioUnitario() {
        return proPrecioUnitario;
    }

    public void setProPrecioUnitario(String proPrecioUnitario) {
        this.proPrecioUnitario = proPrecioUnitario;
    }

    public String getPromoFechaini() {
        return promoFechaini;
    }

    public void setPromoFechaini(String promoFechaini) {
        this.promoFechaini = promoFechaini;
    }

    public String getPromoFechafin() {
        return promoFechafin;
    }

    public void setPromoFechafin(String promoFechafin) {
        this.promoFechafin = promoFechafin;
    }

    public String getProImagen() {
        return proImagen;
    }

    public void setProImagen(String proImagen) {
        this.proImagen = proImagen;
    }
}