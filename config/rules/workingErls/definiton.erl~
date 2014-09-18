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
					entity.field("BSTMI").value() != "" && entity.field("BSTMI").value() == "5000"
				</condition>
			</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.field("BSTMI").quality.setAccuracy(true)
					</consequence>
				</consequence-list>
			</then>
		<url>"http://www.google.com"</url>
	</rule>
</ruleset>
