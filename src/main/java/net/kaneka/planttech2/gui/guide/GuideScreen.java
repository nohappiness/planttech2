package net.kaneka.planttech2.gui.guide;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class GuideScreen extends Screen
{
	protected static final ResourceLocation BACKGROUND = new ResourceLocation("planttech2:textures/gui/guide.png");
	protected int xSize = 441;
	protected int ySize = 258;
	protected int guiLeft;
	protected int guiTop;
	
	protected final List<Widget> buttons_mainmenu = Lists.newArrayList();
	protected final List<Widget> buttons_entry = Lists.newArrayList();
	protected final List<Widget> buttons_navigation = Lists.newArrayList();
	private Guide guide = new Guide(); 
	
	int mode = 0; //0: mainmenu, 1: entrys, 2: details  
	int menuid = 0;
	int entryid = 0; 
	int page = 0; 
	int maxPages = 0; 
	int linesPerPage = 19; 
	
	private List<GuideString> lines = new ArrayList<GuideString>(); 
	private List<GuidePicture> pics; 
	
	
	public GuideScreen()
	{
		super(new TranslationTextComponent("planttech2.guide"));
	}
	
	//When directly open a entry
	public GuideScreen(int menuid, int entryid)
	{
		this(); 
		this.mode = 2; 
		this.menuid = menuid; 
		this.entryid = entryid;
	}
	
	@Override
	public void init()
	{
		super.init();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		for(int x = 0; x < 2; x ++)
		{
			for(int y = 0; y < 5; y++)
			{
				addButtonList(buttons_mainmenu, 1, x*5 + y, this.guiLeft + 50 + x * 200, this.guiTop + 35 + y * 37 , 150, 35, translateUnformated("guide.planttech2.amenu"));
			}
		}
		
		for(int x = 0; x < 2; x ++)
		{
			for(int y = 0; y < 5; y++)
			{
				addButtonList(buttons_entry, 2, x*5 + y, this.guiLeft + 50 + x * 200, this.guiTop + 35 + y * 37 , 150, 20, translateUnformated("guide.planttech2.submenu"));
			}
		}
		
		addButtonList(buttons_navigation, 0, 0, this.guiLeft + 170, this.guiTop + 230, 100, 20, translateUnformated("gui.back")); 
		addButtonList(buttons_navigation, 0, 1, this.guiLeft + 15, this.guiTop + 230, 100, 20, translateUnformated("gui.last")); 
		addButtonList(buttons_navigation, 0, 2, this.guiLeft + 320, this.guiTop + 230, 100, 20, translateUnformated("gui.next")); 
		
		deactivateButtonList(buttons_entry);
		
		deactivateButtonList(buttons_navigation);
		
		update(true); 
		
	}
	
	protected void addButtonList(List<Widget> list, int buttontype, int id, int xPos, int yPos, int width, int height, String displayString)
	{
		CustomButton button = addButton(new CustomButton(id, xPos, yPos, width, height, translateUnformated(displayString), (btn)->
		{
			GuideScreen.this.buttonClicked(buttontype, id); 
		}));
		list.add(button);
	}
	
	protected void buttonClicked(int buttontype, int buttonid)
	{
		boolean modechange = false; 
		if(buttontype == 0) 
		{
			if(buttonid == 0)
			{
				if(mode >= 1)
				{
					mode--; 
					page = 0; 
				}
			}
			else if(buttonid == 1)
			{
				page--; 
			}
			else if(buttonid == 2)
			{
				page++; 
			}
		}
		else
		{
			modechange = true; 
			mode = buttontype;
			if(mode == 1)
			{
				menuid = buttonid; 
			}
			else if(mode == 2)
			{
				entryid = buttonid + page * 10; 
			}
		}
		update(modechange); 
	}
	
	private void activateButton(Widget button) 
	{
		button.active = true; 
		button.visible = true; 
	}
	
	private void deactivateButton(Widget button) 
	{
		button.active = false; 
		button.visible = false; 
	}
	
	@SuppressWarnings("unused")
	private void activateButtonList(List<Widget> list)
	{
		for(Widget button: list)
		{
			activateButton(button);
		}
	}
	
	private void deactivateButtonList(List<Widget> list)
	{
		for(Widget button: list)
		{
			deactivateButton(button);
		}
	}
	
	private void update(boolean modechange)
	{
		deactivateButtonList(buttons); // deactivate all buttons
		if(mode == 0)
		{
    		for(int i = 0; i < buttons_mainmenu.size(); i++)
    		{
    			Widget button = buttons_mainmenu.get(i); 
    			if(i < guide.getAmountMainMenus())
    			{ 
    				activateButton(button);
    				button.setMessage(new StringTextComponent(guide.getMenuById(i).getName()));
    			}
    		}
		}
		else if(mode == 1)
		{
			GuideMenu menu = guide.getMenuById(menuid);
			maxPages = menu.getAmountEntrys()/8; 
			for(int i = 0; i < buttons_entry.size(); i++)
    		{
    			Widget button = buttons_entry.get(i); 
    			if(i + page * 10 < menu.getAmountEntrys())
    			{ 
    				activateButton(button);
    				button.setMessage(new StringTextComponent(menu.getEntryById(i + page * 10).getName()));
    			}
    		}
			activateButton(buttons_navigation.get(0));
			if(page > 0)
			{
				activateButton(buttons_navigation.get(1));
			}
			
			if(maxPages-1 > page)
			{
				activateButton(buttons_navigation.get(2));
			}
		}
		else if(mode == 2)
		{
			activateButton(buttons_navigation.get(0));
			if(modechange)
			{
				loadEntry();
			}
			
			if(page > 0)
			{
				activateButton(buttons_navigation.get(1));
			}
			
			if(maxPages-1 > page)
			{
				activateButton(buttons_navigation.get(2));
			}
		}
	}
	
	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(mStack);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(BACKGROUND);
		
		this.drawBackground(mStack);
		this.drawForeground(mStack);
		this.drawButtons(mStack, mouseX, mouseY, partialTicks);
		this.drawStrings(mStack);
		this.drawTooltips(mouseX, mouseY);
		
	}

	private void drawButtons(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < this.buttons.size(); ++i)
		{
			this.buttons.get(i).render(mStack, mouseX, mouseY, partialTicks);
		}
	}

	protected void drawBackground(MatrixStack mStack)
	{
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);
	}

	protected void drawForeground(MatrixStack mStack)
	{
		if(mode == 2)
		{
			if(!pics.isEmpty() && page == 0)
			{
				RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
				for(GuidePicture pic: pics)
				{
    				minecraft.getTextureManager().bindTexture(pic.getResloc());
    				blit(mStack, this.guiLeft + pic.getXStart() + 25, this.guiTop + pic.getYStart() + 30, 0, 0, pic.getWidth(), pic.getHeight(), pic.getWidth(), pic.getHeight());
				}
			}
		}
	}
	
	protected void drawStrings(MatrixStack mStack)
	{
		if(mode == 0)
		{
			this.drawCenteredString(mStack, translateUnformated("guide.planttech2.header"), this.guiLeft + (this.xSize / 2), this.guiTop + 15 );
		}
		else if(mode == 1)
		{
			this.drawCenteredString(mStack, guide.getMenuById(menuid).getName(), this.guiLeft + (this.xSize / 2), this.guiTop + 15 );
		}
		else if(mode == 2)
		{	
		
			
			this.drawCenteredString(mStack, guide.getMenuById(menuid).getEntryById(entryid).getHeader().getString(), this.guiLeft + (this.xSize / 2), this.guiTop + 15 );
			for(int i = 0; i < lines.size(); i++)
			{
				GuideString guideString = lines.get(i);
				//System.out.println(guideString.getLine() + ":" + guideString.getString()); 
				if(page * 19 <= guideString.getLine() && guideString.getLine() < page * 19 + 19)
				{
					this.drawLine(mStack, guideString.getString(), this.guiLeft + 25 + guideString.getX(), this.guiTop + 30 + 10 * (guideString.getLine() - (19 * page)));
				}
			}
		}
	}
	
	protected void drawTooltips(int mouseX, int mouseY)
	{

	}
	
	protected String translateUnformated(String name)
	{
		return new TranslationTextComponent(name).getString();
	}
	
	protected void drawCenteredString(MatrixStack mStack, String string, int posX, int posY)
	{
		font.drawString(mStack, string, posX - (font.getStringWidth(string) / 2), posY, Integer.parseInt("000000", 16));
	}
	
	protected void drawLine(MatrixStack mStack, String text, int x, int y)
	{
	    font.drawString(mStack, text, x, y, Integer.parseInt("000000",16));
	}
	
	protected void loadEntry()
	{
		GuideEntry entry = guide.getMenuById(menuid).getEntryById(entryid); 
		pics = entry.getPictures(); 
		lines = entry.getTextFormated(font); 
		/*
		String[] array = entry.getText().split("<br>");
		lines.clear(); 
		String[] array2; 
		String combined = ""; 
		int charcounter = 0; 
		for(int i = 0; i < array.length; i++)
		{
			if(array[i].length() > 0)
			{
				if(Math.ceil(((double)array[i].length())/charsPerLine) > 1) 
				{
					array2 = array[i].split("\\s+");
					combined = ""; 
					charcounter = 0; 
					for(int k = 0; k < array2.length; k++)
					{
						if(array2[k].length() + charcounter > charsPerLine)
						{
							lines.add(combined); 
							combined = array2[k] + " "; 
							charcounter = array2[k].length() + 1; 
						}
						else
						{
							combined += array2[k] + " "; 
							charcounter += array2[k].length() + 1; 
						}
					}
					lines.add(combined); 
				}
				else
				{
					lines.add(array[i]);
				}
			}
			else
			{
				lines.add("");
			}
		}
		*/
		page = 0; 
		maxPages = (int) (Math.ceil((double)lines.size()/linesPerPage));
	}

}
