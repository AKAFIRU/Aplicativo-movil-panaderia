package com.example.panmovil_panitasproject.modulos;

public class Usuario {
    String usumoId, usumoCorreo, usumoContrasena, usumoTelefono;

    public Usuario(String usumoId, String usumoCorreo, String usumoContrasena, String usumoTelefono) {
        this.usumoId = usumoId;
        this.usumoCorreo = usumoCorreo;
        this.usumoContrasena = usumoContrasena;
        this.usumoTelefono = usumoTelefono;
    }

    public String getUsumoId() {
        return usumoId;
    }

    public void setUsumoId(String usumoId) {
        this.usumoId = usumoId;
    }

    public String getUsumoCorreo() {
        return usumoCorreo;
    }

    public void setUsumoCorreo(String usumoCorreo) {
        this.usumoCorreo = usumoCorreo;
    }

    public String getUsumoContrasena() {
        return usumoContrasena;
    }

    public void setUsumoContrasena(String usumoContrasena) {
        this.usumoContrasena = usumoContrasena;
    }

    public String getUsumoTelefono() {
        return usumoTelefono;
    }

    public void setUsumoTelefono(String usumoTelefono) {
        this.usumoTelefono = usumoTelefono;
    }

}
