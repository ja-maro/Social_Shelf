import UserListitem from "./UserListItem";
import "../styles/Address.scss";

const AddressListItem = ({ address }) => {
    return (
        <li>
            <div className="address">
                <div className="address_details">
                    <div>
                        <UserListitem
                            key={address.user.user_id}
                            item={address.user}
                        />
                    </div>
                    <div>{address.id}</div>
                    <div>
                        {address.street} {address.postalCode} {address.city}
                    </div>
                    <div>{address.country}</div>
                </div>
            </div>
        </li>
    );
};

export default AddressListItem;
