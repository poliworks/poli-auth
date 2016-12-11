(ns poli-auth.model.user
  (:require [schema.core :as s]
            [poli-auth.model.common :refer :all]
            [schema.coerce :as coerce]))

(def user-skeleton {:user/id              {:schema s/Uuid :required true}
                    :user/email           {:schema s/Str :required true}
                    :user/type            {:schema s/Keyword :required true}
                    :user/hashed-password {:schema s/Str :required false}
                    :user/password        {:schema s/Str :required false}
                    :user/token           {:schema s/Str :required false}})
(def User (gen-schema user-skeleton))

(def coerce-to-user (coerce/coercer User coerce/json-coercion-matcher))
