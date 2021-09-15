package com.incloud.hcp.jco.tripulantes.dto;

public class PDFZarpeConstantes {

    public static String titulo = "DECLARACIÓN DIARIA DE ZARPE PARA NAVES PESQUERAS";
    public static String titulo2 = "DE ARQUERO BRUTO MAYOR DE 10";
    public static String capitania= "CAPITANIA GUARDACOSTAS MARÍTIMAS DE: ";
    public static String uno="1.- Nombre de la nave: ";
    public static String dos="2.- Nro de matrícula: ";
    public static String tres="3.- A.B. ";
    public static String cuatro="4.- Zona de pesca: ";
    public static String cinco="5.- Tiempo de operación: ";
    public static String seis="6.- Día y hora estimada arribo: ";
    public static String siete="7.- Representante acreditado: ";
    public static String ocho="8.- DECLARO BAJO JURAMENTO ";
    public static String ochoA="QUE LAS VIGENCIAS DE LAS LIBRETAS DE EMBARQUE CON QUE ZARPA LA TRIPULACIÓN Y DE";
    public static String ochoB="LOS CERTIFICADOS DE LA NAVE, SON LOS MISMOS QUE FIGURAN EN LAS LIBRETAS DE EMBARQUE Y EN LOS CERTIFICADOS ";
    public static String ochoC="CORRRESPONDIENTES, ASÍ COMO LA NAVE REÚNE LAS CONDICIONES ESTABLECIDAS PARA NAVEGAR EN FORMA SEGURA.";
    public static String ochoD="";
    public static String nueve="9.- Rol de tripulación";
    public static String diez="10.- Certificados de: ";
    public static String once="11.- En caso de emergencia comunicar a: ";
    public static String onceA="A) Nombre        ";
    public static String onceB="B) Direccion     ";
    public static String onceC="C) Teléfono/Fax  ";
    public static String doce="12.- Nombre del patrón";
    public static String trece="13.- Fecha";
    public static String catorce="14.- D.N.I";
    public static String firmaPatron="FIRMA DEL PATRÓN";
    public static String firma= "_____________________________";
    public static String capitaniaGuardacosta="CAPITANÍA GUARDACOSTA";
    public static String nota="NOTA:";
    public static String notaUno="* ESTA DECLARACIÓN DEBE TRAMITARSE EN LA CAPITANÍA GUARDACOSTAS Y CONSERVESE EL ORIGINAL A BORDO DURANTE LA NAVEGACIÓN.";
    public static String notaDos="* LA TRAMITACIÓN DE LA PRESENTE AUTORIZACIÓN ES GRATUITA. LAS CAPITANIAS GUARDACOSTAS NO SOLICITARAN DOCUMENTACIÓN ADICIONAL PARA AUTORIZAR EL ";
    public static String notaDos1="  ZARPE DIARIO.";
    public static String notaTres="* CUALQUIER QUEJA O DENUNCIA DEBERÁ COMUNICARSE EN FORMA INMEIDATA AL CAPITÁN DE PUERTO Y/O PRIMER AYUDANTE DE LA JURISDICCIÓN A LOS TELEFONOS DE ";
    public static String notaTres1="  LA RELACIÓN QUE SE ENCUENTRA EN EL DORSO DE LA DECLARACIÓN DE ARRIBO O AL SIGUIENTE CORREO ELECTRÓNICO dicapi@marina.mil.pe (DICAPI 1003 - DIRCONTROL)";

    //Campos de las tablas


    //TABLA DZATR Rol Tripulacion
    public static String NOMBR="NOMBR";
    public static String NRLIB ="NRLIB";
    public static String FEVIG ="FEVIG";
    public static String STEXT ="STEXT";

    //TABLA ZATRP Campos
    public static String DSWKS ="DSWKS";
    public static String MREMB ="MREMB";
    public static String TOPER ="TOPER";
    public static String FEARR  ="FEARR";
    public static String HRARR ="HRARR";
    public static String RACRE ="RACRE";
    public static String DSEMP ="DSEMP";
    public static String DFEMP ="DFEMP";
    public static String TFEMP  ="TFEMP";
    public static String NRDNI  ="NRDNI";
    public static String FEZAT  ="FEZAT";
    public static String CDZPC  ="CDZPC";
    public static String AQBRT  ="AQBRT";

    //TABLA VGCER
    public static String DSCER   ="DSCER";
    public static String DSETP   ="DSETP";
    public static String FECCF   ="FECCF";

    //Fields Rol Tripulacion
    public static String[]fieldRolTripulacion= new String[]{"", "Apellidos y Nombres", "Matricula", "Vigencia", "Cargo a bordo"};
    public static String[]fieldCertificados= new String[]{"", "", "Fecha de Vencimiento"};


}
