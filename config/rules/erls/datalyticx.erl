<ruleset name="DataLyticx">
	<variable-list>
		<variable>
			<type>com.oxymedical.core.commonData.HICData</type>
			<id>dataObject</id>
		</variable>
		<variable>
			<type>com.oxyent.datalyticx.DataLyticxEntity</type>
			<id>entity</id>
		</variable>
	</variable-list>
	<rule name="TestRule1">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(entity.getType() == "MaterialMaster")
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
