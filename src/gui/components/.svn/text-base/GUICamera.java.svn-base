package gui.components;

import factory.*;
import factory.interfaces.*;
import gui.interfaces.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import shared.*;
import shared.enums.*;

/**
 * CSCI 200 Factory Project Version v1
 * 
 * GUICamera class
 * 
 * Camera takes picture of kit or nest, and analyzes.
 * 
 * @author Navya Prakash
 * @version v0
 */

@SuppressWarnings("serial")
public class GUICamera extends GUIComponent implements GUIComponentInterface, GUICameraInterface
{
	private enum states { IDLE, FLASHING, ANALYZING };
	states myState;

	ArrayList <GUIPart> myList	= new ArrayList<GUIPart>(); //set to whichever parts list is given by CameraAgent
	ArrayList <Integer> numParts = new ArrayList<Integer>();

	PartType nestType1, nestType2;
	ArrayList<GUIPart> nest1Parts = new ArrayList<GUIPart>();
	ArrayList<GUIPart> nest2Parts = new ArrayList<GUIPart>();

	ArrayList <PartQuality> qualityList = new ArrayList<PartQuality>(); //returns to cameraAgent the qualities of parts in nest1
	ArrayList <PartQuality> qualityList2 = new ArrayList<PartQuality>(); //returns qualities of parts in nest2

	ArrayList <KitPartsChecklist> myKitOrder = new ArrayList<KitPartsChecklist>();

	GUIKitStand myKitStand; //reference to a kitStand to get list of parts in kit
	CameraSystem agent; //reference to camera agent

	boolean kitCamera, nestCamera;

	private int xPos = 0;
	private int yPos = 0;

	private int flashCounter = 0;
	private int analysisCounter = 0;		// Optimization? One counter instead of two? bc never two states at once

	boolean badPartDetected, kitIsGood, kitMissingParts;

	// Set the image for the camera flash
	private final ImageIcon image = new ImageIcon("images/cameraflash.png");
	private int width = image.getIconWidth();
	private int height = image.getIconHeight();
	ArrayList <PartType> missingParts = new ArrayList<PartType>();

	/*
	 * ELIAS ADDED THIS
	 */
	KitConfig kitConfig1;
	KitConfig kitConfig2;


	class KitPartsChecklist 
	{
		PartType myType;
		int numParts;

		public KitPartsChecklist(PartType type)
		{
			myType = type;
			numParts = 1;
		}

		public KitPartsChecklist(PartType type, int np)
		{
			myType = type;
			numParts = np;
		}
	}


	//Called by camera agent
	@Override
	public void goIdle()
	{
		//myState = states.IDLE;
	}
	@Override
	public void setCameraSystemAgent(CameraSystem cameraSystemAgent)
	{
		agent = cameraSystemAgent;
	}
	// Set the GUICamera's reference to the GUIKitStand
	public void setKitStand(GUIKitStand k)
	{
		myKitStand = k;
	}


	@Override
	public void analyzeKitPicture()
	{
		badPartDetected = false; //boolean in order to give message back to agent
		kitIsGood = true;
		kitMissingParts = false;

		
		
		for (GUIPart p : myList) 
		{
			// If a part is null (shouldn't happen), missing, or bad, reject the kit on the dot
			if(p == null || p.getState() == GUIPart.State.MISSING)
			{
				kitIsGood = false;
				//missingParts.add(p.getType());
			}
			
			if (p.getState() == GUIPart.State.BAD)
			{
				kitIsGood = false;
				badPartDetected = true;
				System.out.println("GUICamera: Bad part detected -- Kit tossed");
				agent.msgKitIsBad();
				return;
			}
		}

		// If the parts are all good, continue by checking the part types against the given KitOrder
		if (!badPartDetected) 
		{
			int kitSize = 0;
			for(KitPartsChecklist kpc : myKitOrder)
			{
				kitSize += kpc.numParts;
			}

			// If the number of parts in the kit is not equal to the number of parts in the kit order, kit is
			// missing parts and should send back a list of missing parts
			if(myList.size() != kitSize)
			{
				kitIsGood = false;
				kitMissingParts = true;
				missingParts.clear();	// Clear the list to make way for the incoming list
			}

			// Check the part types in the kit against the kit order
			for(int i = 0 ; i < myList.size() ; i++)
			{
				// If there is a null in the list (which there shouldn't be), handle the exception by tossing the kit
				if(myList.get(i) == null)
				{
					kitIsGood = false;
					agent.msgKitIsBad();
					return;
				}
				
				// Loop through the AL of KitPartsChecklist and check to see if the part types match up in the correct
				// amounts (check that there are no excess parts)
				for(int j = 0 ; j < myKitOrder.size() ; j++)
				{
					if(myList.get(i).getType() == myKitOrder.get(j).myType)
					{
						if(myKitOrder.get(j).numParts == 0)
						{
							kitIsGood = false;
							agent.msgKitIsBad();
							return;
						}
						else
						{
							myKitOrder.get(j).numParts--;
						}
					}
				}
			}
		}
		
		if (kitMissingParts)
		{
			// Check the parts missing from the list
			for(int k = 0; k < myKitOrder.size(); k++)
			{
				System.out.println("GUICamera: Part type: " + myKitOrder.get(k).myType);
				System.out.println("GUICamera: Number of parts remaining: " + myKitOrder.get(k).numParts);
				if(myKitOrder.get(k).numParts > 0)
				{
					for (int a = 0; a < myKitOrder.get(k).numParts ; a++)
					{
						missingParts.add(myKitOrder.get(k).myType);
						System.out.println(myKitOrder.get(k).myType);
					}
				}
			}
			agent.msgKitHasMissingParts(missingParts);
		}
		else
		{
			kitIsGood = true;
			agent.msgKitIsGood();
		}
	}


	@Override
	public void takeKitPicture(KitOrder k)
	{
		myState = states.FLASHING;
		kitCamera = true;

		// Clear myKitOrder to make way for the new one
		myKitOrder.clear();

		int partTypeIndex = 0;

		// Set the local KitPartsChecklist (an AL that contains elements comprised of a part type and the
		// number of that part type in the kit)
		for(int i = 0 ; i < k.kitOrder.size(); i++)
		{
			//myKitOrderTypes.add(k.kitOrder.get(i));
			boolean partInList = false;

			// Check to see if the part exists in the listing, and if it does, add to the number of the parts in
			// the existing entry
			for(int j = 0 ; j < myKitOrder.size() ; j++)
			{
				if(k.kitOrder.get(i) == myKitOrder.get(j).myType)
				{
					partInList = true;
					partTypeIndex = j;
					break;
				}
			}
			if(!partInList)
			{
				KitPartsChecklist kpc = new KitPartsChecklist(k.kitOrder.get(i));
				myKitOrder.add(kpc);
			}
			else
			{
				myKitOrder.get(partTypeIndex).numParts++;
			}	
		}
		
		myList = (myKitStand.getKitFromStand(0)).getPartsList(); //sets list to be analyzed from kit stand
	}

	@Override
	public void givePart(GUIPart part, GUIComponent giver)
	{
		// Function from higher class, doesn't do anything

	}
	@Override
	public GUIPart getPart(int position)
	{
		// Function from higher class, doesn't do anything
		return null;
	}
	@Override
	public void gotPart(GUIPart part)
	{
		// Function from higher class, doesn't do anything

	}



	@Override
	public void takeNestPicture(ArrayList<GUIPart> nest1, ArrayList<GUIPart> nest2)
	{
		myState = states.FLASHING;
		nestCamera = true;

		nest1Parts.clear();
		nest2Parts.clear();

		// Set the internal lists to the nest1 and nest2 part lists sent in
		for(GUIPart p : nest1)
		{
			nest1Parts.add(p);
		}
		for(GUIPart p : nest2)
		{
			nest2Parts.add(p);
		}

		System.out.println("GUICamera: Taking picture!!");
		System.out.println("Nest 1: " + nest1.size() + "Nest 2: " + nest2.size());
	}

	public void analyzeNestPicture()
	{
		if(!qualityList.isEmpty())
			qualityList.clear();
		if(!qualityList2.isEmpty())
			qualityList2.clear();

		badPartDetected = false;

		for (GUIPart p : nest1Parts)
		{
			if(p == null)
			{
				qualityList.add(PartQuality.MISSING);
			}
			else if (p.getState() == GUIPart.State.GOOD)
			{
				qualityList.add(PartQuality.GOOD);
			}
			else if (p.getState() == GUIPart.State.BAD)
			{
				qualityList.add(PartQuality.BAD);
			}
			else if (p.getState() == GUIPart.State.UKNOWN)
			{
				qualityList.add(PartQuality.UNKNOWN);
			}
		}

		for (GUIPart p : nest2Parts)
		{
			if(p == null)
			{	
				qualityList2.add(PartQuality.MISSING);
			}
			else if (p.getState() == GUIPart.State.GOOD)
			{
				qualityList2.add(PartQuality.GOOD);
			}
			else if (p.getState() == GUIPart.State.BAD)
			{
				qualityList2.add(PartQuality.BAD);
			}
			else if (p.getState() == GUIPart.State.UKNOWN)
			{
				qualityList2.add(PartQuality.UNKNOWN);
			}
		}

		System.out.println("Quality list 1: " + qualityList.size());
		System.out.println("Quality list 2: " + qualityList2.size());

		agent.msgHereIsNestList(qualityList, qualityList2, this); //gives list of part qualities to camera agent

	}


	public void update() {
		if(myState == states.FLASHING)
		{
			flashCounter++;
			if(flashCounter == 20)
			{
				flashCounter = 0;
				myState = states.ANALYZING;
			}
		}
		else if(myState == states.ANALYZING)
		{
			analysisCounter++;
			if(analysisCounter == 100)
			{
				analysisCounter = 0;
				if(nestCamera)
				{
					analyzeNestPicture();
				}
				else if(kitCamera)
				{
					analyzeKitPicture();
				}

				// Reset booleans and return to IDLE
				nestCamera = false;
				kitCamera = false;
				myState = states.IDLE;
			}
		}
	}
	@Override
	public void draw(Graphics g) {
		// Draws camera flash image
		if (myState == states.FLASHING)
		{
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		}
	}

	/*---------------------------*
	 *    Getters and Setters    *
	 *---------------------------*/
	@Override
	public String getName() {
		// // Function from higher class, doesn't do anything
		return null;
	}

	// Set X and Y coordinates of the camera flash
	public void setX(int x) 
	{
		xPos = x;
	}
	public void setY(int y) 
	{
		yPos = y;
	}
	public int getX() 
	{
		return xPos;
	}
	public int getY() 
	{
		return yPos;
	}
	@Override
	public int getWidth() 
	{
		return width;
	}
	@Override
	public int getHeight() {
		return height;
	}

	/*
	 * ELIAS ADDED THIS
	 */
	@Override
	public void setPartTypeForNests(PartType p1, PartType p2) {
		// Set the part types of the nests
		nestType1 = p1;
		nestType2 = p2;
	}

}
