package ar.edu.ort.bmon.rootsapp.constants;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ar.edu.ort.bmon.rootsapp.R;

public class Constants {
    public static final String GOOGLE_ACCOUNT = "google_account";
    ///Firebase DB Collection Selectors
    public static final String PLANT_COLLECTION  = "plants";
    public static final String SPECIES_COLLECTION = "species";
    public static final String MATERIAL_COLLECTION = "materials";
    public static final String EVENTS_COLLECTION = "events";
    //Firebase Plant Fields
    public static final String PLANT_IMAGE_URI = "imageUri";
    //Firebase Storage Collection Selectors
    public static final String PLANT_IMAGES_FOLDER = "/images/plants";
    //Opciones del menu Crear una nueva entrada
    public static final String ADD_NEW_ENTRY_TITLE = "Crear Nueva Entrada";
    public static final String ADD_NEW_PLANT = "Nueva Planta";
    public static final String ADD_NEW_SPECIES = "Nueva Especie";
    public static final String ATTACH_IMAGE_TO_PLANT_ENTRY_TITLE = "Agregar Imagen";
    public static final String ATTACH_IMAGE_TO_PLANT_MESSAGE = "Desea agregar una imagen a esta planta?";
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
    public static final String NEXT_BUTTON = "Siguiente";
    //Identificadores de Intents de Galeria y Camara
    public static final int REQUEST_IMAGE_CAPTURE = 100;
    public static final int GALLERY_REQUEST = 101;
    //Mensajes de la aplicacion
    public static final String PLANT_CREATE_SUCCESS = "Se agrego la planta exitosamente";
    public static final String PLANT_CREATE_ERROR = "Hubo un error al crear la planta, intente nuevamente";
    public static final String MATERIAL_CREATE_SUCCESS = "Se agregaron materiales exitosamente";
    public static final String MATERIAL_CREATE_ERROR = "Hubo un error al agregar los materiaes";
    public static final String IMAGE_GALLERY_ERROR = "Hubo un error al seleccionar la foto";
    public static final String FIREBASE_STORAGE_ERROR = "Hubo un error al subir la foto. Por favor, intente nuevamente";
    //Error Tags
    public static final String FIREBASE_ERROR = "Firebase_Error";
    public static final String STORAGE_ERROR = "Storage Error";
    //Detalle de planta. Agregar Tareas
    public static final String ADD_NEW_TASK_TITLE = "Crear Nueva Tarea";
    public static final String ADD_TASK_FUMIGATE = "Fumigar";
    public static final String ADD_TASK_PRUNE = "Podar";
    public static final String ADD_TASK_FERTILIZE = "Fertilizar";
    public static final String ADD_TASK_RAISE_HUMIDITY = "Subir humedad";
    public static final String ADD_TASK_LOWER_HUMIDITY = "Bajar humedad";
    public static final String ADD_TASK_CHECK_PLAGES = "Revisar plagas";
    // Eventos
    public static final String CREATE_NEW_EVENT_TITLE = "Crear un evento";
    public static final String GERMINATION = "Germinaciones";
    public static final String CUTTING = "Esquejes";
    public static final String SELECTED_EVENT = "Selected Event";
    public static final String[] EVENT_OPTIONS = new String[]{ "Subir humedad", "Bajar humedad", "Revisar plagas"};
    public static final int DEFAULT_REMAINDER_DURATION = 1;
    public static final String ADD_TASK_TO_EVENT_SUCCESS = "Tarea agregada satisfactoriamente";
    public static final String SPECIES_SELECTION_DIALOG = "Seleccione una especie";
    public static final String CREATE_EVENT_SUCCESS = "Evento creado satisfactoriamente";
    public static final String CREATE_EVENT_ERROR = "Hubo un problema al crear el evento, intente nuevamente";
    public static final String DELETE_EVENT_SUCCESS = "Evento eliminado satisfactoriamente";
    public static final String DELETE_EVENT_ERROR = "Hubo un problema al eliminar el evento, intente nuevamente";
    public static final String MISSING_DATA_INPUTS_EVENT = "Por favor complete todos los campos";
    public static final String GRUPOS = "Grupos";
}
