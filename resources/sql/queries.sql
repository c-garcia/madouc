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


