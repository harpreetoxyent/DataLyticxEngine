<ruleset name="workflowpattern0">
	<variable-list>
		<variable>
			<type>com.oxyent.datalyticx.DataLyticxEntity</type>
			<id>entity</id>
		</variable>
	</variable-list>
	<rule name="CheckLotSize">
		<salience> 0 </salience>
			<if>
			<condition-list>
				<condition name="cond1">
					entity.field("BSTMA").value() != "" && entity.field("BSTMA").value() == "5000"
				</condition>
			</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.field("BSTMA").quality.accuracy(true)
					</consequence>
				</consequence-list>
			</then>
		<url>"http://www.google.com"</url>
	</rule>
	<rule name="CheckMaterialType">
		<salience> 0 </salience>
			<if>
			<condition-list>
				<condition name="cond1">
					entity.field("BSTMA").value() == "5000" && entity.field("MTART").value() == "corrugate"
				</condition>
			</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.field("BSTMA").quality.accuracy(true)
					</consequence>
				</consequence-list>
			</then>
		<url>"http://www.yahoo.com"</url>
	</rule>
	<rule name="CheckAgainstLegitimateValue">
		<salience> 1 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					entity.getType() == "Plant" || entity.getType() == "Material Master" || entity.getType() == "Work Center"
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					entity.checkQuality()
				</consequence>
			</consequence-list>
			</then>
		<url> "http://www.google.com" </url>
	</rule>
</ruleset>
