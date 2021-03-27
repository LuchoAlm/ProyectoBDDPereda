package sample.modelo;

public class beanCita {
    public String getId_cita() {
        return id_cita;
    }

    public void setId_cita(String id_cita) {
        this.id_cita = id_cita;
    }

    public String getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
    }

    public String getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(String id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getCedula_paciente() {
        return cedula_paciente;
    }

    public void setCedula_paciente(String cedula_paciente) {
        this.cedula_paciente = cedula_paciente;
    }

    public String getCedula_odontologo() {
        return cedula_odontologo;
    }

    public void setCedula_odontologo(String cedula_odontologo) {
        this.cedula_odontologo = cedula_odontologo;
    }

    public String getId_tratamiento() {
        return id_tratamiento;
    }

    public void setId_tratamiento(String id_tratamiento) {
        this.id_tratamiento = id_tratamiento;
    }

    public String getId_hclinica() {
        return id_hclinica;
    }

    public void setId_hclinica(String id_hclinica) {
        this.id_hclinica = id_hclinica;
    }

    public beanCita(String id_cita,
                    String fecha_cita,
                    String hora_cita,
                    String id_sucursal,
                    String cedula_paciente,
                    String cedula_odontologo,
                    String id_tratamiento,
                    String id_hclinica) {
        this.id_cita = id_cita;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.id_sucursal = id_sucursal;
        this.cedula_paciente = cedula_paciente;
        this.cedula_odontologo = cedula_odontologo;
        this.id_tratamiento = id_tratamiento;
        this.id_hclinica = id_hclinica;
    }

    private String id_cita;
    private String fecha_cita;
    private String hora_cita ;
    private String id_sucursal;
    private String cedula_paciente;
    private String cedula_odontologo;
    private String id_tratamiento;
    private String id_hclinica;
}
