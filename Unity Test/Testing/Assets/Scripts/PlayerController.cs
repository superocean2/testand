using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour
{
    private Rigidbody2D rigid;
    public int speed;
    private int count;
    public Text countText;
    public Text winText;
    public void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
        count = 0;
        SetCountText();
        winText.text = "";
    }

    public void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Horizontal");
        float moveVertical = Input.GetAxis("Vertical");
        Vector2 v = new Vector2(moveHorizontal, moveVertical);
        rigid.AddForce(v * speed);
    }

    public void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.CompareTag("PickUp"))
        {
            collision.gameObject.SetActive(false);
            count++;
            SetCountText();
            if (count>=10)
            {
                winText.text = "You Won!";
            }
        }
    }
    private void SetCountText()
    {
        countText.text = string.Format("Count: {0}", count);
    }
}
