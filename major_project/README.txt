SID: 490477658

References:
https://stackoverflow.com/questions/66896061/javafx-i-need-to-set-a-value-from-an-event-handler-to-a-variable
(Referenced in CurrencyView.java)

Previous Commits:
Milestone 1 at commit ee992b5244dfed80a9297bd062d0d97cd890b3d1
https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/ee992b5244dfed80a9297bd062d0d97cd890b3d1/major_project

Indication of Completeness:
DISTINCTION
(MVC was partially implemented and kept in, but only because it was not worth the
effort to change back)

Optional features:
 - About (c)
 - Light/Dark mode (c)
 - Personalisation and User accounts (d)
    - Light/Dark mode persistent when logging out and back in
    - Currencies in viewing table are also persistent
    - Background colour of the map can be chosen (and is persistent)

Instructions and Notes:
 - Other colours on map are not customisable, unclear if the specs want the user to pick one
   theme colour or individual colours, see comments in code for more justification
   /clarification.
 - A good way to demonstrate the concurrency is to spam the change theme button during API calls.
 - There is a controller class, but threading is still implemented in the View
