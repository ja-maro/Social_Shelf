const UserListItem = ({ item }) => (
    <div>
        <div>({item.user_id}) {item.username}</div>
        <div>{item.role}</div>
    </div> 
);

export default UserListItem;