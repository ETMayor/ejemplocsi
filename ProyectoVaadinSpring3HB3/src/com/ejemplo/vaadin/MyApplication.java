package com.ejemplo.vaadin;

import com.ejemplo.vaadin.admusuarios.UsuarioForm;
import com.ejemplo.vaadin.entidades.Usuario;
import com.ejemplo.vaadin.servicios.ServicioUsuarios;
import com.vaadin.Application;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yo
 * @version
 */
public class MyApplication extends Application implements ClickListener{
	private static final long serialVersionUID = 1539466153154709086L;
	UsuarioForm formUser = null;
	 Button btnReset = null;
	 Button btInsertar = null;
	private String saludo = "hola";
	
	@Autowired
    ServicioUsuarios servicioUsuarios;

    @Override
    public void init() {
        Window mainWindow = new Window("Aplicacion");
        Label label = new Label("<h2>Ejemplo de Vaadin, Hibernate y Spring MVC/REST</h2>", Label.CONTENT_XHTML);
        
        mainWindow.addComponent(label);
        formUser = new UsuarioForm();
        //Button btnReset  = new Button();
        
        
        btnReset = formUser.getBtnReset();
        btInsertar = formUser.getBtInsertar();
        //asociamos el listener a los botones
        btnReset.addListener(this);
        btInsertar.addListener(this);
        
        
        
        mainWindow.addComponent(formUser);
        setMainWindow(mainWindow);
    }

    /**
     * Metodo que retorna el objeto injectado para que otras clases de la
     * aplicacion puedan usarlo
     *
     * @return El objeto ServicioUsuario inyectado
     */
    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }



	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		formUser.getWindow().showNotification("botonPulsado");
		if(event.getSource() == btInsertar && formUser.isValid()){
			Usuario usuario = formUser.formularioAEntidad();
			Integer respuesta =getServicioUsuarios().guardarUsuario(usuario);
			
			switch (respuesta.intValue()) {
			case 0:
				formUser.getWindow().showNotification("se ha guardado el usuario");
				
				break;
				case 1:
					formUser.getWindow().showNotification("Ususario no exsite");	
					break;
				case 2:
					formUser.getWindow().showNotification("no dispones de permisos");	
					break;
				case 4:
					formUser.getWindow().showNotification("ya existe el usuario");	
					break;
				default:
					formUser.getWindow().showNotification("error al guardar");	
					break;
			}
			
			}else{
				formUser.getWindow().showNotification("rellene los datos del form");
			
			
			
		}
		
		
		if(event.getSource() == btnReset ){
		formUser.getTxtNombre().setValue("");
		formUser.getTxtApellidos().setValue("");
		formUser.getTxtClave().setValue("");
		formUser.getTxtCorreo().setValue("");
		
			
		}
		
	}


}
