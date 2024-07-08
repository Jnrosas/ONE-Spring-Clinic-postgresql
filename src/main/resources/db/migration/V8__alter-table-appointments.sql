alter table appointments add column active boolean;

update appointments set active=true;
