package org.jetbrains.dukat.model.commonLowerings

import org.jetbrains.dukat.astModel.FunctionModel
import org.jetbrains.dukat.astModel.FunctionTypeModel
import org.jetbrains.dukat.astModel.ModuleModel
import org.jetbrains.dukat.astModel.ParameterModifierModel
import org.jetbrains.dukat.astModel.SourceSetModel
import org.jetbrains.dukat.astModel.transform
import org.jetbrains.dukat.ownerContext.NodeOwner

private class NoinlineLowering : ModelWithOwnerTypeLowering {
    override fun lowerFunctionModel(ownerContext: NodeOwner<FunctionModel>, parentModule: ModuleModel): FunctionModel {
        val node = ownerContext.node
        val contextResolved = if (node.inline) {
            val nodeResolved = node.copy(parameters = node.parameters.map {
                if (it.type is FunctionTypeModel) {
                    it.copy(modifier = ParameterModifierModel.NOINLINE)
                } else {
                    it
                }
            })
            ownerContext.copy(node = nodeResolved)
        } else {
            ownerContext
        }
        return super.lowerFunctionModel(contextResolved, parentModule)
    }
}

fun ModuleModel.addNoinlineModifier(): ModuleModel {
    return NoinlineLowering().lowerRoot(this, NodeOwner(this, null))
}

fun SourceSetModel.addNoinlineModifier(): SourceSetModel {
    return transform {
        it.addNoinlineModifier()
    }
}