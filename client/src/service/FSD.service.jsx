import { usefetchwrapper } from "../utils/fetchwrapper";

export const service=()=>{

    const fetchwrapper=usefetchwrapper();
    const baseurl='/sso';

    function login(name,password)
    {
        const url=`${baseurl}/login`;
        return fetchwrapper.post(url,{
            name: name,
            password: password,
          });
    }

    function getpage()
    {
        const url=`${baseurl}/pass`;
        return fetchwrapper.get(url);
    }

    function adduser(name,password,email)
    {
        const url=`${baseurl}/add`;
        return fetchwrapper.post(url,{
            name: name,
            password: password,
            email:email
          });
    }

    return{
        login,
        getpage,
        adduser
    };

}

