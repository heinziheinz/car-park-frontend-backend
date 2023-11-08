import {Link} from "react-router-dom";

const LinkComponent = ({url, value}) => {
    return (
        <Link to={url}>
            <button type="button">{value}</button>
        </Link>
    );
}
export default LinkComponent;