package uy.edu.ort.obligatorio.peajes.dominio;

public class BonificacionPropietarioPuesto {
    private Bonificacion bonifiacion;
    private Puesto puesto;
    private Propietario propietario;

    public BonificacionPropietarioPuesto(Bonificacion boni, Propietario prop, Puesto puesto){
        this.bonifiacion = boni;
        this.propietario = prop;
        this.puesto = puesto;
    }

    public Bonificacion getBonificacion(){
        return bonifiacion;
    }

    public Propietario getPropietario(){
        return propietario;
    }

    public Puesto getPuesto(){
        return puesto;
    }

}
