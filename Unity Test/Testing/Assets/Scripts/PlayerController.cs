using UnityEngine;
using System.Collections;

public class PlayerController : MonoBehaviour
{
    private Rigidbody2D rigid;
    public int speed;

    public void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
    }

    public void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Horizontal");
        float moveVertical = Input.GetAxis("Vertical");
        Vector2 v = new Vector2(moveHorizontal, moveVertical);
        rigid.AddForce(v*speed);
    }
}
