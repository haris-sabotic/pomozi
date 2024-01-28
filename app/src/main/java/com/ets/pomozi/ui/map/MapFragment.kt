package com.ets.pomozi.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.ets.pomozi.databinding.FragmentMapBinding
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.util.GlobalData
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager


class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup map
        binding.map.mapboxMap
            .apply {
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(GlobalData.ORGANIZATIONS[0].longitude, GlobalData.ORGANIZATIONS[0].latitude))
                        .zoom(9.0)
                        .build()
                )
            }

        // add markers to map
        binding.map.mapboxMap.loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            for (org in GlobalData.ORGANIZATIONS) {
                addAnnotationToMap(org.longitude, org.latitude)
            }
        }

        // set up filters

        val arraySpinner = GlobalData.ORGANIZATIONS.map { it.name }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.mapFiltersSpinner.adapter = adapter


        binding.mapButtonFilters.setOnClickListener {
            binding.mapButtonFilters.visibility = View.GONE
            binding.mapLayoutFilters.visibility = View.VISIBLE
        }

        binding.mapFiltersButtonSave.setOnClickListener {
            binding.mapButtonFilters.visibility = View.VISIBLE
            binding.mapLayoutFilters.visibility = View.GONE

            var organization: OrganizationModel = GlobalData.ORGANIZATIONS[0]
            for (org in GlobalData.ORGANIZATIONS) {
                if (org.name == binding.mapFiltersSpinner.selectedItem) {
                    organization = org
                    break
                }
            }

            binding.map.mapboxMap
                .apply {
                    setCamera(
                        CameraOptions.Builder()
                            .center(Point.fromLngLat(organization.longitude, organization.latitude))
                            .zoom(15.0)
                            .build()
                    )
                }
        }
    }

    private fun addAnnotationToMap(longitude: Double, latitude: Double) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            requireContext(),
            com.ets.pomozi.R.drawable.red_marker
        )?.let {
            val annotationApi = binding.map.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(longitude, latitude))
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(it)
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
