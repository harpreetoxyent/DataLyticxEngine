<ruleset name="workflowpattern0">
	<variable-list>
		<variable>
			<type>com.oxyent.datalyticx.DataLyticxEntity</type>
			<id>entity</id>
		</variable>
		<variable>
			<type>com.oxyent.datalyticx.Field</type>
			<id>field</id>
		</variable>
	</variable-list>
	<rule name="CheckMaterialType">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					((field.getName() == "BSTMI") && (field.getValue() =="5000") && (field.getName() == "MTART") && (field.getValue == "corrugate"))
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					entity.field.quality.accuracy=true
				</consequence>
			</consequence-list>
		</then>
		<url> "http://www.google.com" </url>
	</rule>
</ruleset>
