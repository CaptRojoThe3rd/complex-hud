package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.PositionInfo;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.position.PositionBase;
import com.captrojo.complexhud.position.PositionBottomCenter;
import com.captrojo.complexhud.position.PositionBottomLeft;
import com.captrojo.complexhud.position.PositionBottomRight;
import com.captrojo.complexhud.position.PositionMiddleCenter;
import com.captrojo.complexhud.position.PositionMiddleLeft;
import com.captrojo.complexhud.position.PositionMiddleRight;
import com.captrojo.complexhud.position.PositionTopCenter;
import com.captrojo.complexhud.position.PositionTopLeft;
import com.captrojo.complexhud.position.PositionTopRight;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ClientEventHandler
{
	static PositionBase[] apos;
	
	static
	{
		apos = new PositionBase[PositionOrigin.values().length];
		apos[0] = new PositionTopLeft();
		apos[1] = new PositionTopCenter();
		apos[2] = new PositionTopRight();
		apos[3] = new PositionMiddleLeft();
		apos[4] = new PositionMiddleCenter();
		apos[5] = new PositionMiddleRight();
		apos[6] = new PositionBottomLeft();
		apos[7] = new PositionBottomCenter();
		apos[8] = new PositionBottomRight();
	}
	
	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
		
		for (PositionBase pb : apos) {
			if (pb != null) {
				pb.reset();
			}
		}
		
		for (RegisteredElement re : HUDElementList.element_list) {
			re.reloadValues1();
			if (!re.to_be_rendered) {
				continue;
			}
			re.reloadValues2();
			if (re.element.isFixed()) {
				re.pos = new PositionInfo(0, re.width - 1, 0, re.height - 1);
				continue;
			}
			int i = re.element.getPosOrigin().ordinal();
			apos[i].setInitialPos(re);
		}
		
		for (PositionBase pb : apos) {
			if (pb != null) {
				pb.calcDims();
				pb.calcOffset(event.resolution);
			}
		}
		
		for (RegisteredElement re : HUDElementList.element_list) {
			if (!re.to_be_rendered || re.element.isFixed()) {
				continue;
			}
			int i = re.element.getPosOrigin().ordinal();
			apos[i].applyAllOffsets(re, event.resolution, re.element.getPosOperation());
			re.element.render(event.resolution, event.mouseX, event.mouseY, event.partialTicks, re.pos);
		}
	}
}
