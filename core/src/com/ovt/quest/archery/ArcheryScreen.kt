package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : ScreenAdapter() {

    lateinit var sb: SpriteBatch
    lateinit var imul: InputMultiplexer

    lateinit var tilemap: TiledMap
    lateinit var tilemapRenderer: OrthogonalTiledMapRenderer
    lateinit var camera: OrthographicCamera
    lateinit var world: World
    lateinit var box2DDebugRenderer: Box2DDebugRenderer

    lateinit var objectFactory: ObjectFactory

    lateinit var bow: Bow
    lateinit var target: Target

    lateinit var scaler: Scaler


    override fun show() {
        sb = SpriteBatch()

        camera = OrthographicCamera()
        camera.setToOrtho(false, 8f, 4.8f)
        world = World(Vector2(0f, -10f), true)
        box2DDebugRenderer = Box2DDebugRenderer(true, true, true, true, true, true)

        scaler = Scaler(camera = camera)

        tilemap = TmxMapLoader().load("maps/archery/basic/archery-sample.tmx")
        tilemapRenderer = OrthogonalTiledMapRenderer(tilemap, scaler.unitScale, sb)


        objectFactory = ObjectFactory(world, tilemap, scaler)

        bow = objectFactory.createBow()
        target = objectFactory.createTarget()

        val zone = objectFactory.getZone()

        imul = InputMultiplexer(BowControlListener(zone, scaler, bow, objectFactory), CameraInputProcessor(camera), KeyInputProcessor(camera))
        Gdx.input.inputProcessor = imul
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(1/60f, 6, 2)

        camera.update()
        tilemapRenderer.setView(camera)
        tilemapRenderer.render()
        box2DDebugRenderer.render(world, camera.combined)

//        update()
//
        sb.begin()
        bow.draw(sb)
        target.draw(sb)
        sb.end()
    }

    fun update() {

        fun normalize(b: Vector2) = Math.sqrt(b.x.toDouble() * b.x.toDouble() + b.y.toDouble() * b.y.toDouble()).toFloat()
        fun dot(a: Vector2, b: Vector2) = a.x * b.x + a.y + b.y
        val dragConstant = 0.001f

        if (Bodies.arrow != null) {
            val arrow = Bodies.arrow!!
            world.clearForces()

            val flightSpeed = normalize(arrow.linearVelocity)
            val bodyAngle = arrow.angle
            val pointingDirection = Vector2(MathUtils.cos(bodyAngle), -MathUtils.sin(bodyAngle))
            val flyingAngle = MathUtils.atan2(arrow.linearVelocity.y, arrow.linearVelocity.x)
            val flightDirection = Vector2(MathUtils.cos(flyingAngle), MathUtils.sin(flyingAngle))
            val dot = dot(flightDirection, pointingDirection)
            val dragForceMagnitude = (1 - Math.abs(dot)*flightSpeed*flightSpeed*dragConstant*arrow.mass)
            val arrowTailPosition = arrow.getWorldPoint(Vector2(-1.4f, 0f))
            arrow.applyForce(Vector2(dragForceMagnitude*-flightDirection.x, -dragForceMagnitude*-flightDirection.y), arrowTailPosition, true)

        }

        /*
        			world.Step(1/30,5,5);
			world.ClearForces();
			for (var i:Number=arrowVector.length-1; i>=0; i--) {
				var body:b2Body=arrowVector[i];
				var flightSpeed:Number=Normalize2(body.GetLinearVelocity());
				var bodyAngle:Number=body.GetAngle();
				var pointingDirection:b2Vec2=new b2Vec2(Math.cos(bodyAngle),- Math.sin(bodyAngle));
				var flyingAngle:Number=Math.atan2(body.GetLinearVelocity().y,body.GetLinearVelocity().x);
				var flightDirection:b2Vec2=new b2Vec2(Math.cos(flyingAngle),Math.sin(flyingAngle));
				var dot:Number=b2Dot(flightDirection,pointingDirection);
				var dragForceMagnitude:Number=(1-Math.abs(dot))*flightSpeed*flightSpeed*dragConstant*body.GetMass();
				var arrowTailPosition:b2Vec2=body.GetWorldPoint(new b2Vec2(-1.4,0));
				body.ApplyForce(new b2Vec2((dragForceMagnitude*-flightDirection.x),(dragForceMagnitude*-flightDirection.y)),arrowTailPosition);

				if (body.GetPosition().x*worldScale>500) {
					for (var c:b2ContactEdge=body.GetContactList(); c; c=c.next) {
						var contact:b2Contact=c.contact;
						var fixtureA:b2Fixture=contact.GetFixtureA();
						var fixtureB:b2Fixture=contact.GetFixtureB();
						var bodyA:b2Body=fixtureA.GetBody();
						var bodyB:b2Body=fixtureB.GetBody();
						if (bodyA.GetUserData()=="wall"||bodyB.GetUserData()=="wall") {
							arrowVector.splice(i,1);
						}
					}
				}
			}
			world.DrawDebugData();
         */
    }
}