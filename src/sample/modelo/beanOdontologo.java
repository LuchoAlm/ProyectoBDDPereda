package sample.modelo;

public class beanOdontologo {
    public String getCedula_odon() {
        return cedula_odon;
    }

    public void setCedula_odon(String cedula_odon) {
        this.cedula_odon = cedula_odon;
    }

    public String getNombres_odon() {
        return nombres_odon;
    }

    public void setNombres_odon(String nombres_odon) {
        this.nombres_odon = nombres_odon;
    }

    public String getApellidos_odon() {
        return apellidos_odon;
    }

    public void setApellidos_odon(String apellidos_odon) {
        this.apellidos_odon = apellidos_odon;
    }

    public String getTelefonoFijo_odon() {
        return telefonoFijo_odon;
    }

    public void setTelefonoFijo_odon(String telefonoFijo_odon) {
        this.telefonoFijo_odon = telefonoFijo_odon;
    }

    public String getCelular_odon() {
        return celular_odon;
    }

    public void setCelular_odon(String celular_odon) {
        this.celular_odon = celular_odon;
    }

    public String getEspecialidad_odon() {
        return especialidad_odon;
    }

    public void setEspecialidad_odon(String especialidad_odon) {
        this.especialidad_odon = especialidad_odon;
    }

    public String getId_suc() {
        return id_suc;
    }

    public void setId_suc(String id_suc) {
        this.id_suc = id_suc;
    }

    public beanOdontologo(String cedula_odon,
                          String nombres_odon,
                          String apellidos_odon,
                          String telefonoFijo_odon,
                          String celular_odon,
                          String especialidad_odon,
                          String id_suc) {
        this.cedula_odon = cedula_odon;
        this.nombres_odon = nombres_odon;
        this.apellidos_odon = apellidos_odon;
        this.telefonoFijo_odon = telefonoFijo_odon;
        this.celular_odon = celular_odon;
        this.especialidad_odon = especialidad_odon;
        this.id_suc = id_suc;
    }

    public beanOdontologo() {
    }

    private String cedula_odon;
    private String nombres_odon;
    private String apellidos_odon;
    private String telefonoFijo_odon;
    private String celular_odon;
    private String especialidad_odon;
    private String id_suc;
}
