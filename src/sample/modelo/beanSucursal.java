package sample.modelo;

public class beanSucursal {

    private String id_suc;
    private String nombre_suc;
    private String direccion_suc;
    private String telefono_suc;

    public beanSucursal(String id_suc, String nombre_suc, String direccion_suc, String telefono_suc) {
        this.id_suc = id_suc;
        this.nombre_suc = nombre_suc;
        this.direccion_suc = direccion_suc;
        this.telefono_suc = telefono_suc;
    }



    public String getId_suc() {
        return id_suc;
    }

    public void setId_suc(String id_suc) {
        this.id_suc = id_suc;
    }

    public String getNombre_suc() {
        return nombre_suc;
    }

    public void setNombre_suc(String nombre_suc) {
        this.nombre_suc = nombre_suc;
    }

    public String getDireccion_suc() {
        return direccion_suc;
    }

    public void setDireccion_suc(String direccion_suc) {
        this.direccion_suc = direccion_suc;
    }

    public String getTelefono_suc() {
        return telefono_suc;
    }

    public void setTelefono_suc(String telefono_suc) {
        this.telefono_suc = telefono_suc;
    }


}
