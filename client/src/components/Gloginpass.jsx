import { service } from "../service/FSD.service";
import { useLocation } from "react-router-dom";
import UnauthorizedPage from "./Unauthorized";
import LandingPage from "./Page";
const Gloginpass = (props) => {
    const location = useLocation();
    const { data } = location.state || {};
    const formattedData = JSON.stringify(data, null, 2);
    const isLoginPage = (html) => {
        return html.includes('<h2 class="form-signin-heading">Login with OAuth 2.0</h2>');
    }

    return (
        <div>
          
            {typeof formattedData === 'string' && !isLoginPage(formattedData) ? <LandingPage/> : <UnauthorizedPage />}
        </div>

    );

}

export default Gloginpass;