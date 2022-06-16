SID: 490477658

Input API: CurrencyScoop
Output API: Twilio
Claimed Tier: Distinction
Credit Optional Feature 1: About
Credit Optional Feature 2: Light/Dark mode
Distinction Optional Feature: Personalisation and User accounts
High Distinction Optional Feature: -

Milestone 1 Submission:
    SHA: ee992b5244dfed80a9297bd062d0d97cd890b3d1
    URI: https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/ee992b5244dfed80a9297bd062d0d97cd890b3d1/major_project
Milestone 1 Re-Submission:
    SHA: ee992b5244dfed80a9297bd062d0d97cd890b3d1
    URI: https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/ee992b5244dfed80a9297bd062d0d97cd890b3d1/major_project
Milestone 2 Submission:
    SHA: 6f2143d0d589cafd2b534c5a5c69f98334e13860
    URI: https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/6f2143d0d589cafd2b534c5a5c69f98334e13860/major_project
Milestone 2 Re-Submission:
    SHA: fc5fdf6c72d0b04db3e4138bceda5442617ae699
    URI: https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/fc5fdf6c72d0b04db3e4138bceda5442617ae699/major_project
Exam Base Commit:
    SHA: fc5fdf6c72d0b04db3e4138bceda5442617ae699
    URI: https://github.sydney.edu.au/hgoo9099/SCD2_2022/tree/fc5fdf6c72d0b04db3e4138bceda5442617ae699/major_project
Exam Submission Commit:
    SHA: N/A
    URI: N/A


Instructions and Notes:
 - For the Personalisation  and User accounts feature:
    - Light/Dark mode persistent when logging out and back in.
    - Currencies in viewing table are also persistent.
    - Background colour of the map can be chosen (and is persistent).

 - Exam Extension Notes
    - Extension feature is the special deal currency feature.
    - A currency can be selected for the special deal by clicking the buttons in
    - the special deal column of the main table. It will show a tick for the
    - selected currency.
    - In online mode only one can be selected. In offline, there is only 1
    - default currency so they all show a tick.
    - The selected special deal currency is not cached as part of the
    - personalisation & user accounts. This was not defined in the specifications,
    - so I had to assume it was a no because it would require too much
    - modification to the existing code.
    - All modifications have been made in the view package, so there are no
    - changes to testing.

References:
https://stackoverflow.com/questions/66896061/javafx-i-need-to-set-a-value-from-an-event-handler-to-a-variable
(Referenced in CurrencyView.java)
