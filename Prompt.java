import java.awt.*;
import java.awt.event.*;
public class Prompt extends Frame{
	private Button submit;
    
	/**
	 * Constructor if the prompt does not need a submit button 
	 * @param submitNeeded
	 * @param gridStyle
	 */
	public Prompt(boolean submitNeeded, int gridStyle){
		if(submitNeeded == false){
			this.setLayout(new GridLayout(0,gridStyle));
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt) {
					((Frame)(evt.getSource())).dispose();
				}
			});	
		} else {
			new Prompt().activate();
		}	
		
		/* resizes components and centers the window. */
		this.pack(); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Default constructor for prompts with submit button
	 */
    public Prompt(){	
		this.setLayout(new GridLayout(0,2));
		submit = new Button("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt) {
				((Frame)(evt.getSource())).dispose();
			}
		});	
    }

	/**
	 * This method is called to add functionality to the submit button 
	 * @param listener
	 */
	public void addSubmitListener(ActionListener listener){
		submit.addActionListener(listener);
	}    

	/**
	 * adds submit button to prompt, resizes components and centers the window.
	 */
	public void activate(){	
		this.add(submit);
		this.pack(); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}