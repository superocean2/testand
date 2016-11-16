using UnityEngine;
using System.Collections;

public class CarController : MonoBehaviour
{

    public int speed;
    Vector3 position;
    public UIManager ui;
    public AudioManager am;
    // Use this for initialization
    void Start()
    {
        position = transform.position;
        am.carsound.Play();
    }

    // Update is called once per frame
    void Update()
    {
        position.x += Input.GetAxis("Horizontal") * speed * Time.deltaTime;
        position.x = Mathf.Clamp(position.x, -1.8f, 1.8f);
        transform.position = position;
    }

    public void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("EnemyCar"))
        {
            am.hit.Play();
            am.gameover.Play();
            am.carsound.Stop();
            Destroy(gameObject);
            //Time.timeScale = 0;
            ui.SetGameOver();
        }
    }
}
