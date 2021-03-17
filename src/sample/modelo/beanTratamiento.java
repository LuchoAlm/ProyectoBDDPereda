package sample.modelo;

public class beanTratamiento {
    private String id_trat;
    private String nombre_trat;
    private String descripcion_trat;
    private float precio_trat;

    public String getId_trat() {
        return id_trat;
    }

    public void setId_trat(String id_trat) {
        this.id_trat = id_trat;
    }

    public String getNombre_trat() {
        return nombre_trat;
    }

    public void setNombre_trat(String nombre_trat) {
        this.nombre_trat = nombre_trat;
    }

    public String getDescripcion_trat() {
        return descripcion_trat;
    }

    public void setDescripcion_trat(String descripcion_trat) {
        this.descripcion_trat = descripcion_trat;
    }

    public float getPrecio_trat() {
        return precio_trat;
    }

    public void setPrecio_trat(float precio_trat) {
        this.precio_trat = precio_trat;
    }

    public beanTratamiento(String id_trat, String nombre_trat, String descripcion_trat, float precio_trat) {
        this.id_trat = id_trat;
        this.nombre_trat = nombre_trat;
        this.descripcion_trat = descripcion_trat;
        this.precio_trat = precio_trat;
    }


}
