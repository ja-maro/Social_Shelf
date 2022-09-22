import UserListitem from "./UserListItem";

const AddressListItem = ({ address }) => (
    <li>
        <div>
            <UserListitem key={address.user.user_id} item={address.user} />
        </div>
        <div>{address.id}</div>
        <div>{address.street} {address.postalCode} {address.city}</div>
        <div>{address.country}</div>
        <br />
    </li> 
);

export default AddressListItem;