package com.example.panmovil_panitasproject.modulos;

import java.io.Serializable;

public class Resenas implements Serializable {
        String reId, usumoId, usumoCorreo, reEstrellas, reDescripcion, proId, proNombre;

        public Resenas(String reId, String usumoId, String usumoCorreo, String reEstrellas, String reDescripcion, String proId, String proNombre) {
                this.reId = reId;
                this.usumoId = usumoId;
                this.usumoCorreo = usumoCorreo;
                this.reEstrellas = reEstrellas;
                this.reDescripcion = reDescripcion;
                this.proId = proId;
                this.proNombre = proNombre;
        }

        public String getReId() {
                return reId;
        }

        public void setReId(String reId) {
                this.reId = reId;
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

        public String getReEstrellas() {
                return reEstrellas;
        }

        public void setReEstrellas(String reEstrellas) {
                this.reEstrellas = reEstrellas;
        }

        public String getReDescripcion() {
                return reDescripcion;
        }

        public void setReDescripcion(String reDescripcion) {
                this.reDescripcion = reDescripcion;
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
}
