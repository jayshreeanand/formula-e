class ApplicationParameters < ActionParameter::Base
  def initialize(params)
    super
    map_nested_attributes
  end

  def permit(type = nil)
    required_params.permit(default_attributes)
  end

  def map_nested_attributes
    nested_attributes.each do |attribute|
      if params[root_key].has_key?(attribute)
        params[root_key]["#{attribute}_attributes"] = params[root_key].delete(attribute)
      end
    end
  end

  def required_params
    params.require(root_key)
  end

  def root_key
    raise NotImplementedError.new('You must implement root_key')
  end

  def nested_attributes
    []
  end

  def default_attributes
    []
  end
end
