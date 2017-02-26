-- :name find-users :? :*

select * from users

-- :name count-users-with-credentials :? :1
-- :doc finds a user with :username and :password

select count(*) from users
where username = :username and password = :password

-- :name find-user-by-username :? :1
-- :doc finds a user with :username

select * from users
where username = :username

-- :name save-user! :! :n
-- :doc adds a user with :username :personal-name and :hashed-password

insert into users (username, personal_name, password)
values (:username, :personal-name, :hashed-password)


