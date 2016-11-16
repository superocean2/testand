using UnityEngine;
using System.Collections;

public class CarSpawEnemy : MonoBehaviour {

    public GameObject[] enemyCar;
    int carIndex;
    public float delayTimer=3f;
    float timer;
	// Use this for initialization
	void Start () {
        timer = delayTimer;
	}
	
	// Update is called once per frame
	void Update () {
        timer -= Time.deltaTime;

        if (timer<0)
        {
            Vector3 pos = new Vector3(Random.Range(-1.83f, 1.83f), transform.position.y, transform.position.z);
            carIndex = Random.Range(0, 6);
            Instantiate(enemyCar[carIndex], pos, transform.rotation);
            timer = delayTimer;
        }
	}
}
