package ar.edu.ort.bmon.rootsapp.constants;

public class Constants {
    ///Firebase DB Collection Selectors
    public static final String PLANT_COLLECTION  = "plants";
    public static final String SPECIES_COLLECTION = "species";
    //Firebase Storage Collection Selectors
    public static final String PLANT_IMAGES_FOLDER = "/images/plants";
    //Opciones del menu Crear una nueva entrada
    public static final String ADD_NEW_ENTRY_TITLE = "Crear Nueva Entrada";
    public static final String ADD_NEW_PLANT = "Nueva Planta";
    public static final String ADD_NEW_SPECIES = "Nueva Especie";
    //Constantes del ProgressDialog
    public static final String UPLOADING_PHOTO = "Subiendo foto";
    public static final String PERCENTAGE_COMPLETE = " % Completado";
    //Opciones del menu Cambiar Foto
    public static final String CHANGE_PHOTO_MENU_TITLE = "Cambiar Foto";
    public static final String SELECT_FROM_GALLERY = "Seleccionar de la galeria";
    public static final String TAKE_PHOTO = "Tomar una foto";
    //Botones de los menus
    public static final String CANCEL_BUTTON = "Cancelar";
    public static final String ACCEPT_BUTTON = "Aceptar";
    //Identificadores de Intents de Galeria y Camara
    public static final int REQUEST_IMAGE_CAPTURE = 100;
    public static final int GALLERY_REQUEST = 101;
    //Errores de Aplicacion
    public static final String IMAGE_GALLERY_ERROR = "Hubo un error al seleccionar la foto";
}
