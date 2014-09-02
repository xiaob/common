package com.yuan.common.swing;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SkinTool {
	
	public static void substance(){
//		skin(new SubstanceOfficeBlue2007LookAndFeel());
//		skin(new SubstanceNebulaLookAndFeel());
//		skin(new SubstanceBusinessBlueSteelLookAndFeel());
//		skin(new SubstanceBusinessLookAndFeel());
//		skin(new SubstanceChallengerDeepLookAndFeel());
//		skin(new SubstanceCremeCoffeeLookAndFeel());
//		skin(new SubstanceDustCoffeeLookAndFeel());
//		skin(new SubstanceEmeraldDuskLookAndFeel());
//		skin(new SubstanceGraphiteAquaLookAndFeel());
//		skin(new SubstanceMagellanLookAndFeel());
//		skin(new SubstanceMistSilverLookAndFeel());
//		skin(new SubstanceRavenLookAndFeel());
//		skin(new SubstanceSaharaLookAndFeel());
//		skin(new SubstanceTwilightLookAndFeel());
	}
	
	public static void skin(final LookAndFeel laf){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager.setLookAndFeel(laf);
				} catch (UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

}
