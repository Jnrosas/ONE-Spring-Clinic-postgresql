alter table physicians add active boolean;
update physicians set active=true;
alter table patients add active boolean;
update patients set active=true;