(ns poli-auth.model.user
  (:require [schema.core :as s]))

(def User {(s/required-key :user/id)              s/Int
           (s/required-key :user/email)           s/Str
           (s/optional-key :user/hashed-password) s/Str
           (s/optional-key :user/password)        s/Str
           (s/optional-key :user/token)           s/Str})
