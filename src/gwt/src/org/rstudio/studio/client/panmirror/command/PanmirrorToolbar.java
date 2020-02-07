/*
 * PanmirrorToolbar.java
 *
 * Copyright (C) 2009-20 byRStudio, PBC
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

package org.rstudio.studio.client.panmirror.command;

import java.util.ArrayList;

import org.rstudio.core.client.widget.HasFindReplace;
import org.rstudio.core.client.widget.SecondaryToolbar;
import org.rstudio.core.client.widget.ToolbarButton;
import org.rstudio.core.client.widget.ToolbarMenuButton;
import org.rstudio.studio.client.panmirror.Panmirror;
import org.rstudio.studio.client.workbench.views.source.editors.text.findreplace.FindReplaceBar;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;


public class PanmirrorToolbar extends SecondaryToolbar implements RequiresResize
{
   public PanmirrorToolbar()
   {
      super(false, "Panmirror Editor Toolbar");
      getElement().getStyle().setHeight(getHeight(), Unit.PX);
   }
   
   @Override
   public int getHeight()
   {
      return 23;
   }
 
   public void init(PanmirrorCommand[] commands, HasFindReplace findReplace)
   { 
      PanmirrorCommandIcons icons = PanmirrorCommandIcons.INSTANCE;
      
      commands_ = new PanmirrorToolbarCommands(commands);
      commandObjects_.clear();
      removeAllWidgets();
      
      PanmirrorToolbarRadioMenu blockMenu = createBlockMenu();
      addLeftTextMenu(addRadioMenu(blockMenu));
      
      addLeftSeparator();
      
      formatWidgets_ = addWidgetGroup(
         addLeftButton(Panmirror.EditorCommands.Strong),
         addLeftButton(Panmirror.EditorCommands.Em),
         addLeftButton(Panmirror.EditorCommands.Code),
         addLeftSeparator()
      );
      
      blockWidgets_ = addWidgetGroup(
         addLeftButton(Panmirror.EditorCommands.BulletList),
         addLeftButton(Panmirror.EditorCommands.OrderedList),
         addLeftButton(Panmirror.EditorCommands.Blockquote),     
         addLeftSeparator() 
      );
      
      insertWidgets_ = addWidgetGroup(
         addLeftButton(Panmirror.EditorCommands.Link),
         addLeftButton(Panmirror.EditorCommands.Image),
         addLeftSeparator(),
         addLeftButton(Panmirror.EditorCommands.RmdChunk),
         addLeftSeparator()
      );
      
      PanmirrorToolbarMenu tableMenu = createTableMenu();
      addLeftWidget(new ToolbarMenuButton(ToolbarButton.NoText, ToolbarButton.NoTitle, icons.get(icons.TABLE), tableMenu));
      
      addLeftSeparator();
      
      PanmirrorToolbarMenu formatMenu = createFormatMenu();
      addLeftTextMenu(new ToolbarMenuButton("Format", "Format", null, formatMenu, false));
      
      addLeftSeparator();
      
      PanmirrorToolbarMenu insertMenu = createInsertMenu();
      addLeftTextMenu(new ToolbarMenuButton("Insert", "Insert", null, insertMenu, false)); 
          
      addLeftSeparator();
      findReplaceButton_ = new ToolbarButton(
         ToolbarButton.NoText,
         "Find/Replace",
         FindReplaceBar.getFindIcon(),
         new ClickHandler() {
            public void onClick(ClickEvent event)
            {
               boolean show = !findReplace.isFindReplaceShowing();
               findReplace.showFindReplace(show);
            }
         });
      addLeftWidget(findReplaceButton_);
   }
  
   
   public void sync(boolean images)
   {
      commandObjects_.forEach((object) -> object.sync(images));
      invalidateSeparators();
      onResize();
   }
   
   public void setFindReplaceLatched(boolean latched)
   {
      findReplaceButton_.setLeftImage(latched ? 
         FindReplaceBar.getFindLatchedIcon() : 
         FindReplaceBar.getFindIcon()
     );
   }
   
   @Override
   public void onResize()
   {
      int width = getOffsetWidth();
      if (width == 0)
         return;
           
      showGroup(formatWidgets_, width > 350);
      showGroup(blockWidgets_, width > 450);
      showGroup(insertWidgets_, width > 550);
   }
   
   
   private PanmirrorToolbarRadioMenu createBlockMenu()
   {
      PanmirrorToolbarRadioMenu blockMenu = new PanmirrorToolbarRadioMenu("Normal", "Block Format", commands_);
      blockMenu.addCommand(Panmirror.EditorCommands.Paragraph);
      blockMenu.addSeparator();
      blockMenu.addCommand(Panmirror.EditorCommands.Heading1);
      blockMenu.addCommand(Panmirror.EditorCommands.Heading2);
      blockMenu.addCommand(Panmirror.EditorCommands.Heading3);
      blockMenu.addCommand(Panmirror.EditorCommands.Heading4);
      blockMenu.addCommand(Panmirror.EditorCommands.Heading5);
      blockMenu.addCommand(Panmirror.EditorCommands.Heading6);
      blockMenu.addSeparator();
      blockMenu.addCommand(Panmirror.EditorCommands.CodeBlock);
      return blockMenu;
   }
   
   private PanmirrorToolbarMenu createFormatMenu()
   {
      PanmirrorToolbarMenu formatMenu = new PanmirrorToolbarMenu(commands_);
      PanmirrorToolbarMenu textMenu = formatMenu.addSubmenu("Text");
      textMenu.addCommand(Panmirror.EditorCommands.Strong);
      textMenu.addCommand(Panmirror.EditorCommands.Em);
      textMenu.addCommand(Panmirror.EditorCommands.Code);
      textMenu.addCommand(Panmirror.EditorCommands.Strikeout);
      textMenu.addCommand(Panmirror.EditorCommands.Superscript);
      textMenu.addCommand(Panmirror.EditorCommands.Subscript);
      textMenu.addCommand(Panmirror.EditorCommands.Smallcaps);
      textMenu.addSeparator();
      textMenu.addCommand(Panmirror.EditorCommands.RawInline);
      textMenu.addSeparator();
      textMenu.addCommand(Panmirror.EditorCommands.Span);
      PanmirrorToolbarMenu listMenu = formatMenu.addSubmenu("Bullets & Numbering");
      listMenu.addCommand(Panmirror.EditorCommands.BulletList);
      listMenu.addCommand(Panmirror.EditorCommands.OrderedList);
      listMenu.addCommand(Panmirror.EditorCommands.TightList);
      listMenu.addSeparator();
      listMenu.addCommand(Panmirror.EditorCommands.ListItemCheck);
      listMenu.addCommand(Panmirror.EditorCommands.ListItemCheckToggle);
      listMenu.addSeparator();
      listMenu.addCommand(Panmirror.EditorCommands.ListItemSink);
      listMenu.addCommand(Panmirror.EditorCommands.ListItemLift);
      listMenu.addSeparator();
      listMenu.addCommand(Panmirror.EditorCommands.OrderedListEdit);
      formatMenu.addSeparator();
      formatMenu.addCommand(Panmirror.EditorCommands.Blockquote);
      formatMenu.addCommand(Panmirror.EditorCommands.LineBlock);
      formatMenu.addSeparator();
      formatMenu.addCommand(Panmirror.EditorCommands.Div);
      formatMenu.addCommand(Panmirror.EditorCommands.RawBlock);
      formatMenu.addSeparator();
      formatMenu.addCommand(Panmirror.EditorCommands.AttrEdit);
      return formatMenu;
   }
   
   private PanmirrorToolbarMenu createInsertMenu()
   {
      PanmirrorToolbarMenu insertMenu = new PanmirrorToolbarMenu(commands_);
      insertMenu.addCommand(Panmirror.EditorCommands.Image);
      insertMenu.addCommand(Panmirror.EditorCommands.Link);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.RmdChunk);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.ParagraphInsert);
      insertMenu.addCommand(Panmirror.EditorCommands.HorizontalRule);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.Footnote);
      insertMenu.addCommand(Panmirror.EditorCommands.Citation);
      insertMenu.addSeparator();
      PanmirrorToolbarMenu definitionMenu = insertMenu.addSubmenu("Definition");
      definitionMenu.addCommand(Panmirror.EditorCommands.DefinitionList);
      definitionMenu.addSeparator();
      definitionMenu.addCommand(Panmirror.EditorCommands.DefinitionTerm);
      definitionMenu.addCommand(Panmirror.EditorCommands.DefinitionDescription);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.InlineMath);
      insertMenu.addCommand(Panmirror.EditorCommands.DisplayMath);
      insertMenu.addCommand(Panmirror.EditorCommands.InlineLatex);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.RawBlock);
      insertMenu.addCommand(Panmirror.EditorCommands.RawInline);
      insertMenu.addSeparator();
      insertMenu.addCommand(Panmirror.EditorCommands.YamlMetadata);
      return insertMenu;
   }
   
   private PanmirrorToolbarMenu createTableMenu()
   {
      PanmirrorToolbarMenu tableMenu = new PanmirrorToolbarMenu(commands_);
      tableMenu.addCommand(Panmirror.EditorCommands.TableInsertTable);
      tableMenu.addSeparator();
      tableMenu.addCommand(Panmirror.EditorCommands.TableToggleHeader);
      tableMenu.addCommand(Panmirror.EditorCommands.TableToggleCaption);
      tableMenu.addSeparator();
      PanmirrorToolbarMenu alignMenu = tableMenu.addSubmenu("Align Column");
      alignMenu.addCommand(Panmirror.EditorCommands.TableAlignColumnLeft);
      alignMenu.addCommand(Panmirror.EditorCommands.TableAlignColumnCenter);
      alignMenu.addCommand(Panmirror.EditorCommands.TableAlignColumnRight);
      alignMenu.addSeparator();
      alignMenu.addCommand(Panmirror.EditorCommands.TableAlignColumnDefault);
      tableMenu.addSeparator();
      tableMenu.addCommand(Panmirror.EditorCommands.TableAddRowBefore);
      tableMenu.addCommand(Panmirror.EditorCommands.TableAddRowAfter);
      tableMenu.addCommand(Panmirror.EditorCommands.TableAddColumnBefore);
      tableMenu.addCommand(Panmirror.EditorCommands.TableAddColumnAfter);
      tableMenu.addSeparator();
      tableMenu.addCommand(Panmirror.EditorCommands.TableDeleteRow);
      tableMenu.addCommand(Panmirror.EditorCommands.TableDeleteColumn);
      tableMenu.addCommand(Panmirror.EditorCommands.TableDeleteTable);
      return tableMenu;
   }
  
   
   private Widget addLeftButton(String id)
   {
      return addLeftWidget(addButton(id));
   }
   
   private void addLeftTextMenu(ToolbarMenuButton menuButton)
   {
      addLeftWidget(menuButton);
      menuButton.addStyleName(RES.styles().toolbarTextMenuButton());
   }
   
   private PanmirrorToolbarRadioMenu addRadioMenu(PanmirrorToolbarRadioMenu menu)
   {
      commandObjects_.add(menu);
      return menu;
   }
   
   private PanmirrorCommandButton addButton(String id)
   {
      PanmirrorCommandButton button = new PanmirrorCommandButton(commands_.get(id));
      commandObjects_.add(button);
      return button;
   }
   
   private ArrayList<Widget> addWidgetGroup(Widget...widgets)
   {
      ArrayList<Widget> group = new ArrayList<Widget>();
      for (Widget widget : widgets)
         group.add(widget);
      return group;
   }
   
   private void showGroup(ArrayList<Widget> group, boolean show)
   {
      if (group !=  null) 
      {
         group.forEach((widget) -> {
           widget.setVisible(show);
         });
      }
   }
   
   
   private static final PanmirrorToolbarResources RES = PanmirrorToolbarResources.INSTANCE;
   
   private ArrayList<Widget> formatWidgets_ = null;
   private ArrayList<Widget> insertWidgets_ = null;
   private ArrayList<Widget> blockWidgets_ = null;
  
   private ToolbarButton findReplaceButton_ = null;
   
   private PanmirrorToolbarCommands commands_ = null;
   private ArrayList<PanmirrorCommandUIObject> commandObjects_ = new ArrayList<PanmirrorCommandUIObject>();
}